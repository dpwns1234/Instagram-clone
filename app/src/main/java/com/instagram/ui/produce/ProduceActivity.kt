package com.instagram.ui.produce

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.instagram.R
import com.instagram.TestActivity
import com.instagram.databinding.ActivityProduceBinding
import com.instagram.model.Image
import com.instagram.model.Post
import com.instagram.model.PreviewPost
import com.instagram.model.User
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
// TODO. 다음에 할 거 -> 하트 눌리고 안눌리고 그거 왜 안되는지 + 노트에 써놓은거
class ProduceActivity : AppCompatActivity() {
    private val firebaseUrl =
        "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val databaseRef = Firebase.database(firebaseUrl).reference
    private val uriList = mutableListOf<Uri>()
    private lateinit var binding: ActivityProduceBinding

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
                        this.finish()
                    }
                    // 2. 이미지를 한 장 선택한 경우
                    else if (intent.clipData == null) {
                        val imageUri: Uri = intent.data!!
                        fileToHttpUri()
                        //uriToHttp2(imageUri)
//                        makeImageFile(this, imageUri)?.let { realPathUri ->
//                            uriList.add(realPathUri)
//                        }
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
//                                try {
//                                    makeImageFile(this, imageUri)?.let { realPathUri ->
//                                        uriList.add(realPathUri)
//                                    }
//                                } catch (e: Exception) {
//                                    Log.e("ProduceActivity", "File select error", e)
//                                }
                            }
                        }
                    }
//                    Glide.with(this)
//                        .load(uriList[0]) // viewpager로 바꾸면 for문 사용
//                        .into(imageView)
                }
            }
        }
        return launcher
    }

    private fun loadGallery(launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)  // 다중 이미지를 가져올 수 있도록 세팅
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI  // 이건 뭐지?
        launcher.launch(intent)
    }

    private fun setCheckButton(introduce: AppCompatEditText) {
        val userUid = Firebase.auth.currentUser!!.uid
        val postKey = databaseRef.child("posts").push().key

        // TODO. imageValue에서 왜 toMap을 지금까지 해줬을까? 하고 안 하고의 차이가 뭘까??
        // 이미지 객체 생성
        val imageValueList = mutableListOf<Image>()
        for (i in 0 until uriList.size) {
            val image = Image(i, uriList[i].toString())
            imageValueList.add(image)
        }

        // TODO. 해야할 것 User의 id를 uid로 수정하고 sign-up에서 database 구조 잘 수정
        databaseRef.child("users").get().addOnSuccessListener { snapshot ->
            val createdAt = System.currentTimeMillis()
            val dataFormat = SimpleDateFormat("yyyy-MM-dd")
            val userValue = snapshot.child(userUid).child("profiles").getValue<User>()!!
            val post = Post(postKey,
                userValue,
                imageValueList,
                introduce.text.toString(),
                createdAt = dataFormat.format(createdAt))
            val previewPost =
                PreviewPost(postKey, imageValueList[0].imageUrl, dataFormat.format(createdAt))
            val childUpdates = hashMapOf(
                "posts/$postKey" to post.toMap(),
                "users/$userUid/profiles/posts/$postKey" to previewPost
            )

            databaseRef.updateChildren(childUpdates)
        }
        this.finish()
    }

    // TODO. 파일로 바꾼 후 uri로 만드는 방법 말고 다른 방법은 없을까?
    @Nullable
    fun makeImageFile(context: Context, uri: Uri): File? {
        val contentResolver: ContentResolver = context.contentResolver ?: return null

        // 파일 경로를 만듬
        val filePath: String = (context.applicationInfo.dataDir + File.separator
                + System.currentTimeMillis())
        val file = File(filePath)
        try {
            // 매개변수로 받은 uri 를 통해  이미지에 필요한 데이터를 불러 들인다.
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            // 이미지 데이터를 다시 내보내면서 file 객체에  만들었던 경로를 이용한다.
            val outputStream: OutputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()
        } catch (ignore: IOException) {
            return null
        }
        Log.d("file", "file: $file, ${file.path}")
        return file
    }
    
    // 2가지 방법 중 선택해서 게시물 올리는거 마무리 짓기

    fun fileToHttpUri() {
        val fireStorage = Firebase.storage("gs://instagram-android-65931.appspot.com/")
        //val storageRef = fireStorage.reference.child("post_image")
        val storageRef = fireStorage.reference

        // firebase storage에 파일 올리기
        
        // 그 올린 파일 download uri 반환하기
        storageRef.child("post1.jpg").downloadUrl.addOnSuccessListener { uri ->
            Log.d("file", "fileToHttpUri = $uri")

        }.addOnFailureListener {
            Log.w("ProduceActivity", "Fail bring a uri", it.cause)
        }

    }

    fun uriToHttp2(uri: Uri) {
        val user = Firebase.auth.currentUser!!
        val fireStorage = Firebase.storage("gs://instagram-android-65931.appspot.com/")
        val storageRef = fireStorage.reference.child("post_image")
        //val dataFormat = SimpleDateFormat("yyMMss")
        val fileName = System.currentTimeMillis() / 1000
        val ref = storageRef.child("${user.uid}/$fileName.jpg")
        val uploadTask = ref.putFile(uri)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Log.d("file", "downloadUri = $downloadUri")
                Log.d("file", "urlTask.result = ${task.result}")
            } else {
                // Handle failures
                // ...
            }
        }
        //return urlTask.result
    }

}