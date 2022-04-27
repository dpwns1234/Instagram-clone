package com.instagram.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.instagram.databinding.ActivityEditProfileBinding

class EditProfileActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val firebaseUrl = "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    // TODO. 위에 이거 오류인가? 오류 아니면 지워
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // data binding
        val viewModel = ProfileViewModel()
        viewModel.profile.observe(this) {
            binding.profile = it

            // TODO. 왜 위에걸로 해결이 안되는 것인가??
            binding.etEditName.setText(it.name)
            binding.etEditNickname.setText(it.nickname)
            binding.etEditIntroduce.setText(it.introduce)
        }

        setCloseButton()
        setCheckButton()
    }

    private fun setCloseButton() {
        binding.buttonClose.setOnClickListener {
            this.finish()
        }
    }

    private fun setCheckButton() {
        binding.buttonCheck.setOnClickListener {
            val databaseRef = Firebase.database(firebaseUrl).reference
                .child("users")
                .child(auth.uid!!)
                .child("profiles")

            val name = binding.etEditName.text.toString()
            val nickname = binding.etEditNickname.text.toString()
            val introduce = binding.etEditIntroduce.text.toString()
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
    }
}