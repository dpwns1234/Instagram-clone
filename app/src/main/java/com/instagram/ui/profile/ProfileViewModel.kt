package com.instagram.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.instagram.model.PreviewPost
import com.instagram.model.Profile

class ProfileViewModel(private val userUid: String): ViewModel() {
    private val firebaseUrl = "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"

    private val _profile = MutableLiveData<Profile>()
    var profile: LiveData<Profile> = _profile

    private val _profilePosts = MutableLiveData<List<PreviewPost>>()
    var profilePosts: LiveData<List<PreviewPost>> = _profilePosts

    private val _profileUserPosts = MutableLiveData<List<PreviewPost>>()
    var profileUserPosts: LiveData<List<PreviewPost>> = _profileUserPosts

    init{
        loadProfileFromFirebase()
    }

    private fun loadProfileFromFirebase() {
        val databaseRef = Firebase.database(firebaseUrl)
            .getReference("users/$userUid/profiles")
        setProfile(databaseRef)
    }

    private fun setProfile(databaseRef: DatabaseReference) {
        val profileListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val profile = snapshot.getValue<Profile>()
                profile?.let { it ->
                    _profile.value = it
                }
                setPosts(snapshot.child("posts"))
                setUserPosts(snapshot.child("user_posts"))
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("ProfileViewModel", "loadProfile:onCancelled", error.toException())
            }
        }
        databaseRef.addValueEventListener(profileListener)
    }

    private fun setPosts(snapshot: DataSnapshot) {
        val posts = mutableListOf<PreviewPost>()
        for (post in snapshot.children) {
            post.getValue<PreviewPost>()?.let {
                posts.add(it)
            }
        }
        _profilePosts.value = posts
    }

    private fun setUserPosts(snapshot: DataSnapshot) {
        val userPosts = mutableListOf<PreviewPost>()
        for (post in snapshot.children) {
            post.getValue<PreviewPost>()?.let {
                userPosts.add(it)
            }
        }
        _profileUserPosts.value = userPosts
    }

//    private fun setPosts(databaseRef: DatabaseReference) {
//        val postsListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("ProfileViewModel", "loadProfile:onCancelled", error.toException())
//            }
//        }
//        databaseRef.child("posts").addValueEventListener(postsListener)
//    }

//    private fun setUserPosts(databaseRef: DatabaseReference) {
//        val userPostsListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val userPosts = snapshot.getValue<List<PreviewPost>>()
//                userPosts?.let {
//                    _profileUserPosts.value = it
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("ProfileViewModel", "loadProfile:onCancelled", error.toException())
//            }
//        }
//        databaseRef.child("user_posts").addValueEventListener(userPostsListener)
//    }

}