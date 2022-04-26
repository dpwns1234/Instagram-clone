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

class ProfileViewModel(): ViewModel() {
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
        val auth = Firebase.auth
        val databaseRef = Firebase.database(firebaseUrl).reference
            .child("users")
            .child(auth.currentUser!!.uid)
            .child("profiles")

        setProfile(databaseRef)
    }

    private fun setProfile(databaseRef: DatabaseReference) {
        val profileListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val profile = snapshot.getValue<Profile>()
                val posts = snapshot.child("posts").getValue<List<PreviewPost>>()
                val userPosts = snapshot.child("user_posts").getValue<List<PreviewPost>>()
                if (profile == null) {
                    // TODO. 해당 위치에 저장된 데이터가 없음을 뜻함.
                } else {
                    _profile.value = profile!!
                    _profilePosts.value = posts!!
                    _profileUserPosts.value = userPosts!!
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ProfileViewModel", "loadProfile:onCancelled")
            }
        }
        databaseRef.addValueEventListener(profileListener)
    }
}