package com.instagram.ui.produce

import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.instagram.R
import com.instagram.TestActivity
import com.instagram.model.Image
import com.instagram.model.Post
import com.instagram.model.User
import java.text.SimpleDateFormat

class ProduceActivity : AppCompatActivity() {
    private val storage = Firebase.storage
    private val firebaseUrl = "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val databaseRef = Firebase.database(firebaseUrl).reference
    private val user = Firebase.auth.currentUser!!
    private val uriList = mutableListOf<Uri>()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produce)

        val galleryButton = findViewById<Button>(R.id.button_gallery)
        val imageView = findViewById<ImageView>(R.id.iv_post_image)
        val closeButton = findViewById<ImageButton>(R.id.button_produce_close)
        val checkButton = findViewById<Button>(R.id.button_produce_check)

        var uri: Uri? = null
        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // 사진을 한 장도 선택하지 않았을 때
                if(result.data == null) {
                    Toast.makeText(this, "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                    this.finish()
                }
                // 이미지를 한 장 선택한 경우
                else if (result.data!!.clipData == null) {
                    val imageUri: Uri = result.data!!.data!!
                    uriList.add(imageUri)

                    Glide.with(this)
                        .load(imageUri)
                        .into(imageView)
                }
                // 이미지를 여러 장 선택한 경우
                else {
                    val clipData = result.data!!.clipData

                    // 최대 10장까지만 가능( 근데 그럼 >= 10 해야되는거 아닌가??)
                    if(clipData!!.itemCount > 10) {
                        Toast.makeText(this, "최대 10장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "여러장을 선택하였습니당", Toast.LENGTH_SHORT).show()
                        for(i in 0 until clipData.itemCount) {
                            val imageUri = clipData.getItemAt(i).uri
                            try {
                                uriList.add(imageUri)
                            } catch (e: Exception) {
                                Log.e("ProduceActivity", "File select error", e)
                            }
                        }
                    }
                }

                val intent = result.data
                uri = intent?.data
                Glide.with(this)
                    .load(uri)
                    .into(imageView)

            }
        }

        galleryButton.setOnClickListener {
            loadGallery(launcher)
        }
        //uploadToStorage()

        closeButton.setOnClickListener {
            this.finish()
        }
        checkButton.setOnClickListener {
            // database에 저장 root/posts/post_uid(push)
            val uid = user.uid
            val postKey = databaseRef.child("posts").push().key

            // TODO. imageValue에서 왜 toMap을 지금까지 해줬을까? 하고 안하고의 차이가 뭘까??
            // 이미지 객체 생성
            val imageValueList = mutableListOf<Image>()
            for(i in 0 until uriList.size) {
                val image = Image(i, uriList[i].toString())
                imageValueList.add(image)
            }

            databaseRef.child("users").get().addOnSuccessListener { snapshot ->
                val createdAt = System.currentTimeMillis()
                val dataFormat = SimpleDateFormat("yyyy-MM-dd")
                val post = Post(postKey, snapshot.child(uid).getValue<User>()!!, imageValueList, "이건 게시물 소개에요", createdAt = dataFormat.format(createdAt))
                val postValue = post.toMap()

                val childUpdates = hashMapOf<String, Any>(
                    "posts/$postKey" to postValue,
                    "users/$uid/profiles/posts/$postKey" to postValue // posts가 리스트로 쌓여야 하는데 그냥 다 지워지고 하나의 객체만 남음. 수정
                )

                databaseRef.updateChildren(childUpdates)
            }

            // TODO. 마지막: profile 데이터 못 가져오는거 + 해당 writer 데이터 못 가져오는거(118줄)

            this.finish()
        }

    }

    private fun loadGallery(launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)  // 다중 이미지를 가져올 수 있도록 세팅
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI  // 이건 뭐지?
        launcher.launch(intent)
    }

}