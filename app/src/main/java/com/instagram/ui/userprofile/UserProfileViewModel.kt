package com.instagram.ui.userprofile

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
import com.instagram.model.Profile

class UserProfileViewModel(private val userUid: String) : ViewModel() {
    private val firebaseUrl =
        "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"

    private val _userProfile = MutableLiveData<Profile>()
    var userProfile: LiveData<Profile> = _userProfile

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        val databaseRef = Firebase.database(firebaseUrl)
            .getReference("users/$userUid/profiles")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _userProfile.value = snapshot.getValue<Profile>()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("UserProfileViewModel", "Canceled firebase database call")
            }

        })

    }
}