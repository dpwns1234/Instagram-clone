package com.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.navArgument
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.instagram.databinding.ActivityCommentBinding
import com.instagram.model.Post
import com.instagram.model.User
import com.instagram.ui.profile.ProfileViewModel

class CommentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentBinding
    private val firebaseUrl =
        "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val database = Firebase.database(firebaseUrl)
    private val userUid = Firebase.auth.currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(database) {
            val postUid = intent.getStringExtra("postUid")
            getReference("posts/$postUid").get().addOnSuccessListener { snapshot ->
                binding.post = snapshot.getValue<Post>()
                Log.d("hihi", "hi?22?")
            }

            getReference("users/$userUid/profiles").get().addOnSuccessListener { snapshot ->
                binding.user = snapshot.getValue<User>()
            }
        }

    }
}