package com.instagram.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.instagram.model.Feed
import com.instagram.model.Post
import com.instagram.repository.MainRepository

class HomeViewModel(private val repository: MainRepository): ViewModel() {
    private val firebaseUrl = "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val databaseRef = Firebase.database(firebaseUrl).reference

    private val _post = MutableLiveData<List<Post>>()
    var post: LiveData<List<Post>> = _post

    private val _feed = MutableLiveData<List<Feed>>()
    var feed: LiveData<List<Feed>> = _feed

    init {
        loadMain()
        loadHome()
    }

    private fun loadMain() {
        // Main Repository에서 data 받기.
        val mainData = repository.getAssetData()
        mainData?.let {
            //_post.value = it.post
            _feed.value = it.feed
        }
    }

    private fun loadHome() {
        databaseRef.child("posts").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val posts = mutableListOf<Post>()
                for(post in snapshot.children) {
                    post.getValue<Post>()?.let {
                        posts.add(it)
                    }
                }
                _post.value = posts
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("HomeViewModel.kt", "Failed loadHomeData", error.toException())
            }
        })
    }
}