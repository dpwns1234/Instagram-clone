package com.instagram.ui.produce

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.instagram.databinding.ActivityProduceBinding
import com.instagram.model.*
import com.instagram.network.ApiClient
import java.io.*

class ProduceActivity : AppCompatActivity() {
    private val firebaseUrl =
        "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val databaseRef = Firebase.database(firebaseUrl).reference
    private val uriList = mutableListOf<Uri>()
    private lateinit var binding: ActivityProduceBinding
    private val userUid = Firebase.auth.currentUser!!.uid
    private val fireStorage = Firebase.storage("gs://instagram-android-65931.appspot.com/")
    private val postKey = databaseRef.child("posts").push().key


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProduceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val launcher = activityResultLauncher(binding.ivPostImage)
        binding.buttonGallery.setOnClickListener {
            loadGallery(launcher)
        }
        binding.buttonProduceClose.setOnClickListener {
            this.finish()
        }
        binding.buttonProduceCheck.setOnClickListener {
            setCheckButton(binding.etIntroduce)
        }

    }

    private fun activityResultLauncher(imageView: ImageView): ActivityResultLauncher<Intent> {
        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data.let { intent ->
                    // 1. 사진을 한 장도 선택하지 않았을 때
                    if (intent == null) {
                        Toast.makeText(this, "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                    }
                    // 2. 이미지를 한 장 선택한 경우
                    else if (intent.clipData == null) {
                        val imageUri: Uri = intent.data!!
                        uriList.add(imageUri)
                    }
                    // 3. 이미지를 여러 장 선택한 경우
                    else {
                        val clipData = intent.clipData
                        // 3-1. 최대 10장까지만 가능
                        if (clipData!!.itemCount > 10) {
                            Toast.makeText(this, "최대 10장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                        }
                        // 3-2. 10장 이하 선택하였을 경우
                        else {
                            Toast.makeText(this, "여러장을 선택하였습니당", Toast.LENGTH_SHORT).show()
                            for (i in 0 until clipData.itemCount) {
                                val imageUri = clipData.getItemAt(i).uri
                                uriList.add(imageUri)
                            }
                        }
                    }
                    Glide.with(this)
                        .load(uriList[0]) // viewpager로 바꾸면 for문 사용
                        .into(imageView)
                }
            }
        }
        return launcher
    }

    private fun loadGallery(launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)  // 다중 이미지를 가져올 수 있도록 세팅
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        launcher.launch(intent)
    }

    private fun setCheckButton(introduce: AppCompatEditText) {
        if (uriList.isEmpty()) {
            Toast.makeText(this, "이미지를 선택해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            databaseRef.child("users/$userUid/profiles").get().addOnSuccessListener { snapshot ->
                val createdAt = System.currentTimeMillis()
                val userValue = snapshot.getValue<User>()!!

                // 이미지를 제외한 객체들을 미리 만들어 database에 넣어준다.
                val postValue = Post(postKey,
                    userValue,
                    posts = null,
                    introduce.text.toString(),
                    createdAt = createdAt)

                val previewPostsValue = addPreviewPosts(snapshot, createdAt)

                val childUpdates = hashMapOf(
                    "posts/$postKey" to postValue,
                    "users/$userUid/profiles/posts" to previewPostsValue,
                    "users/$userUid/profiles/post_count" to previewPostsValue.size
                )
                databaseRef.updateChildren(childUpdates).addOnSuccessListener {
                    // last index에 이미지 업로드하기
                    uploadToStorage(previewPostsValue.lastIndex)
                }

            }

            this.finish()
        }
    }

    private fun addPreviewPosts(
        snapshot: DataSnapshot,
        createdAt: Long,
    ): MutableList<PreviewPost> {
        var previewPostsValue =
            snapshot.child("posts").getValue<MutableList<PreviewPost>>()
        // getValue를 했을 때 nullable 한 타입으로 넘어와서 아래와 같은 조건문을 만들어준다.
        if (previewPostsValue == null)
            previewPostsValue = mutableListOf(PreviewPost(postKey, postImage = "", createdAt))
        else {
            previewPostsValue.add(PreviewPost(postKey, postImage = "", createdAt))
        }
        return previewPostsValue
    }


    private fun uploadToStorage(previewPostLastIndex: Int) {
        var cnt = 0 // TODO 문제: downloadUri가 순서대로 오지 않음
        val imageValueList = List<Image?>(uriList.size) { null }.toMutableList()
        for (i in 0 until uriList.size) {
            val fileName = System.currentTimeMillis()
            val storageRef =
                fireStorage.reference.child("post_image").child("$userUid/$fileName.jpg")

            storageRef.putFile(uriList[i]).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                storageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    cnt++
                    val downloadUri = task.result
                    // 첫 번째 downloadUri가 Complete 됐을 때 database의 image를 업데이트 해준다.
                    if (i == 0) {
                        databaseRef.child("users/$userUid/profiles/posts/${previewPostLastIndex}/post_image")
                            .setValue(downloadUri.toString())
                    }
                    val image = Image(i, downloadUri.toString())
                    imageValueList[i] = image
                    // 마지막 downloadUri가 Complete 됐을 때 database의 image들만 업데이트 해준다.
                    if (cnt == uriList.size) {
                        databaseRef.child("posts/$postKey/post_images").setValue(imageValueList)
                    }

                    Toast.makeText(this, "$cnt/${uriList.size} 완료", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // TODO. 압축했을 때 화면 돌아가는 문제 + 1MB 미만은 압축 안 하도록 제한
    // 압축해서 업로드하는 함수
    private fun uploadToStorage2() {
        var cnt = 0                                                            // 이미지 모두 업로드 하기 위해
        val imageValueList =
            List<Image?>(uriList.size) { null }.toMutableList() // 이미지가 등록된 순서대로 나오게 하기 위해

        for (i in 0 until uriList.size) {
            val fileName = System.currentTimeMillis()
            val storageRef =
                fireStorage.reference.child("post_image").child("$userUid/$fileName.jpg")
            val imageByteArray = compressImageUri(uriList[i])
            storageRef.putBytes(imageByteArray!!).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                storageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    cnt++
                    val downloadUri = task.result
                    // 첫 번째 downloadUri가 Complete 됐을 때 database의 image를 업데이트 해준다.
                    if (i == 0) {
                        databaseRef.child("users/$userUid/profiles/posts/$postKey/post_image")
                            .setValue(downloadUri.toString())
                    }
                    val image = Image(i, downloadUri.toString())
                    imageValueList[i] = image
                    // 마지막 downloadUri가 Complete 됐을 때 database의 image들만 업데이트 해준다.
                    if (cnt == uriList.size) {
                        databaseRef.child("posts/$postKey/post_images").setValue(imageValueList)
                    }
                    Toast.makeText(this, "$cnt/${uriList.size} 완료", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun compressImageUri(imageUri: Uri): ByteArray? {
        // aiming for ~500kb max. assumes typical device image size is around 2megs
        val scaleDivider = 4
        return try {

            // 1. Convert uri to bitmap
            val fullBitmap =
                MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

            // 2. Get the downsized image content as a byte[]
            val scaleWidth = fullBitmap.width / scaleDivider
            val scaleHeight = fullBitmap.height / scaleDivider
            val downsizedImageBytes = getDownsizedImageBytes(fullBitmap, scaleWidth, scaleHeight)
            downsizedImageBytes

        } catch (ioEx: IOException) {
            ioEx.printStackTrace()
            null
        }
    }

    @Throws(IOException::class)
    fun getDownsizedImageBytes(
        fullBitmap: Bitmap?,
        scaleWidth: Int,
        scaleHeight: Int,
    ): ByteArray {
        val scaledBitmap =
            Bitmap.createScaledBitmap(fullBitmap!!, scaleWidth, scaleHeight, true)

        // 2. Instantiate the downsized image content as a byte[]
        val baos = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

}