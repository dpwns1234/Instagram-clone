package com.instagram.ui.search

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

class SearchViewModel: ViewModel() {
    private val firebaseUrl = "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val databaseRef = Firebase.database(firebaseUrl).reference

    private val _profiles = MutableLiveData<List<Profile>>()
    var profiles: LiveData<List<Profile>> = _profiles

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        databaseRef.child("users")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val users = mutableListOf<Profile>()
                    for (user in snapshot.children) {
                        user.getValue<Profile>()?.let {
                            users.add(it)
                        }
                    }
                    _profiles.value = users
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("HomeViewModel.kt", "Failed loadHomeData", error.toException())
                }
            })
    }
}