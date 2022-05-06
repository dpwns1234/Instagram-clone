package com.instagram.ui.produce

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.instagram.databinding.ActivityProduceBinding
import com.instagram.model.Image
import com.instagram.model.Post
import com.instagram.model.PreviewPost
import com.instagram.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import kotlin.coroutines.CoroutineContext

// TODO. 다음에 할 거 -> 하트 눌리고 안눌리고 그거 왜 안되는지 + 노트에 써놓은거
class ProduceActivity : AppCompatActivity() {
    private val firebaseUrl =
        "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val databaseRef = Firebase.database(firebaseUrl).reference
    private val uriList = mutableListOf<Uri>()
    private lateinit var binding: ActivityProduceBinding
    private val userUid = Firebase.auth.currentUser!!.uid
    private val fireStorage = Firebase.storage("gs://instagram-android-65931.appspot.com/")
    private val postKey = databaseRef.child("posts").push().key
    private val imageValueList = mutableListOf<Image>()

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
        if(uriList.isEmpty()) {
            Toast.makeText(this, "이미지를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            databaseRef.child("users").get().addOnSuccessListener { snapshot ->
                val createdAt = System.currentTimeMillis()
                val userValue = snapshot.child(userUid).child("profiles").getValue<User>()!!
                val post = Post(postKey,
                    userValue,
                    posts = null,
                    introduce.text.toString(),
                    createdAt = createdAt)
                val previewPost =
                    PreviewPost(postKey, postImage = "", createdAt)
                val childUpdates = hashMapOf(
                    "posts/$postKey" to post.toMap(),
                    "users/$userUid/profiles/posts/$postKey" to previewPost
                )
                databaseRef.updateChildren(childUpdates)
            }
            uploadToStorage()
            this.finish()
        }
    }

    private fun uploadToStorage() {
        var cnt = 0 // TODO 문제: downloadUri가 순서대로 오지 않음
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
                    if(i == 0) {
                        databaseRef.child("users/$userUid/profiles/posts/$postKey/post_image")
                            .setValue(downloadUri.toString())
                    }
                    val image = Image(i, downloadUri.toString())
                    imageValueList.add(image)
                    // 마지막 downloadUri가 Complete 됐을 때 database의 image들만 업데이트 해준다.
                    if(cnt == uriList.size) {
                        databaseRef.child("posts/$postKey/post_images").setValue(imageValueList)
                    }

                    Toast.makeText(this, "$cnt/${uriList.size} 완료", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}