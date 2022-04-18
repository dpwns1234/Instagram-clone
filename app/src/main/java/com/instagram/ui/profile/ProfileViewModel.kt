package com.instagram.ui.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.instagram.AssetLoader
import com.instagram.model.PreviewPost
import com.instagram.model.Profile

class ProfileViewModel(private val context: Context): ViewModel() {

    private val _profile = MutableLiveData<Profile>()
    var profile: LiveData<Profile> = _profile

    private val _profilePosts = MutableLiveData<List<PreviewPost>>()
    var profilePosts: LiveData<List<PreviewPost>> = _profilePosts


    init{
        loadProfile()
    }

    private fun loadProfile() {
        val gson = Gson()

        val assetLoader = AssetLoader(context)
        val jsonData = assetLoader.getJsonString("profile.json")
        val profileData = gson.fromJson(jsonData, Profile::class.java)

        _profile.value = profileData
        _profilePosts.value = profile.value?.posts
    }
}