package com.instagram.ui.comment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.instagram.model.Comment
import com.instagram.model.Post
import com.instagram.model.User

class CommentViewModel(private val postUid: String, private val userUid: String): ViewModel() {
    private val firebaseUrl =
        "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val database = Firebase.database(firebaseUrl)


    private val _post = MutableLiveData<Post>()
    var post: LiveData<Post> = _post

    // TODO. comments 없애도 되겠다. 안되겠다.
    private val _comments = MutableLiveData<MutableList<Comment>>()
    var comments: LiveData<MutableList<Comment>> = _comments

    private val _user = MutableLiveData<User>()
    var user: LiveData<User> = _user

    init {
        loadComments()
        loadUser()
    }

    private fun loadComments() {
        database.getReference("posts/$postUid").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue<Post>().let { post ->
                    _post.value = post
                    _comments.value = post?.comments
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("CommentViewModel", "Cancelled load commends data")
            }
        })
    }

    private fun loadUser() {
        database.getReference("users/$userUid/profiles").get().addOnSuccessListener { snapshot ->
            _user.value = snapshot.getValue<User>()
        }
    }

}