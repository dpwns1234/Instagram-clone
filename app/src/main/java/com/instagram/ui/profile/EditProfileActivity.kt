package com.instagram.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.instagram.R
import com.instagram.databinding.ActivityEditProfileBinding
import com.instagram.model.PreviewPost

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val firebaseUrl =
        "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val user = Firebase.auth.currentUser!!
    private val userProfilePath = "users/${user.uid}/profiles"
    private val database = Firebase.database(firebaseUrl)
    private val fireStorage = Firebase.storage("gs://instagram-android-65931.appspot.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO. inflate가 되지 않는 이유가 뭘ㄲ까??
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_edit_profile)
        val viewModel = ProfileViewModel(user.uid)
        viewModel.profile.observe(this) {
            binding.profile = it

            // TODO. 왜 위에걸로 해결이 안되는 것인가??
            binding.etEditName.setText(it.name)
            binding.etEditNickname.setText(it.nickname)
            binding.etEditIntroduce.setText(it.introduce)
        }
        val launcher = activityResultLauncher()

        setProfileImageButton(launcher)
        setCloseButton()
        setCheckButton()
    }

    private fun setProfileImageButton(launcher: ActivityResultLauncher<Intent>) {
        binding.layoutEditProfileImage.setOnClickListener {
            openGallery(launcher)
        }
    }

    private fun setCloseButton() {
        binding.buttonClose.setOnClickListener {
            this.finish()
        }
    }

    private fun setCheckButton() {
        binding.buttonCheck.setOnClickListener {
            val databaseRef = database.getReference(userProfilePath)
            val name = binding.etEditName.text.toString()
            val nickname = binding.etEditNickname.text.toString()
            val introduce = binding.etEditIntroduce.text.toString()

            // 사용자의 프로필을 변경한다.
            editProfile(databaseRef, name, nickname, introduce)
            // 사용자의 모든 게시물의 데이터를 변경한다.
            editProfileOfPost(databaseRef, name, nickname, introduce)
        }
    }

    private fun editProfile(
        databaseRef: DatabaseReference,
        name: String,
        nickname: String,
        introduce: String,
    ) {
        databaseRef.updateChildren(mapOf("name" to name, "nickname" to nickname,
            "introduce" to introduce))
            .addOnSuccessListener {
                Toast.makeText(this, "프로필이 변경되었습니다.", Toast.LENGTH_SHORT).show()
                this.finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "프로필을 변경하지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun editProfileOfPost(
        databaseRef: DatabaseReference,
        name: String,
        nickname: String,
        introduce: String,
    ) {
        val postUidList = mutableListOf<String?>()
        databaseRef.child("posts").get().addOnSuccessListener { snapshot ->
            // postUid 만들기
            for (post in snapshot.children) {
                val postValue = post.getValue<PreviewPost>()
                postValue?.let {
                    postUidList.add(it.postUid)
                }
            }

            // database에서 변경하기
            for (i in 0 until postUidList.size) {
                val databasePostRef = database.getReference("posts/${postUidList[i]}/writer")
                databasePostRef.updateChildren(mapOf(
                    "name" to name,
                    "nickname" to nickname,
                    "introduce" to introduce
                ))
            }
        }
    }

    private fun activityResultLauncher(): ActivityResultLauncher<Intent> {
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
                        uploadProfileImage(imageUri)
                        this.finish()
                    }
                }
            }
        }
        return launcher
    }

    private fun uploadProfileImage(imageUri: Uri) {
        val fileName = user.uid + (System.currentTimeMillis() / 1000)
        val fireStorageRef = fireStorage.getReference("user/profile/$fileName.jpg")

        fireStorageRef.putFile(imageUri).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            fireStorageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                val databaseRef = database.getReference(userProfilePath)
                // 사용자의 프로필 사진을 변경한다.
                databaseRef.child("profile_image").setValue(downloadUri.toString())

                // 사용자의 모든 게시물의 프로필 이미지를 변경한다.
                val postUidList = mutableListOf<String?>()
                databaseRef.child("posts").get().addOnSuccessListener { snapshot ->
                    // postUid 만들기
                    for (post in snapshot.children) {
                        val postValue = post.getValue<PreviewPost>()
                        postValue?.let {
                            postUidList.add(it.postUid)
                        }
                    }

                    // database에서 변경하기
                    for (i in 0 until postUidList.size) {
                        database.getReference("posts/${postUidList[i]}/writer/profile_image")
                            .setValue(downloadUri.toString())
                    }
                    Toast.makeText(this, "프로필 이미지가 변경되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun openGallery(launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        launcher.launch(intent)
    }
}
