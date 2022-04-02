package com.instagram

import android.util.Log
import androidx.core.graphics.component1
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.instagram.databinding.ItemPostImageBinding
import com.instagram.model.Feed
import com.instagram.model.Image
import com.instagram.model.Main
import com.instagram.model.Post
import com.instagram.repository.MainRepository

class MainViewModel(private val repository: MainRepository): ViewModel() {
    private val _post = MutableLiveData<List<Post>>()
    var post: LiveData<List<Post>> = _post

    private val _feed = MutableLiveData<List<Feed>>()
    var feed: LiveData<List<Feed>> = _feed

    init {
        loadMain()
    }

    private fun loadMain() {
        // Main Repository에서 data 받기.
        val mainData = repository.getAssetData()
        mainData?.let {
            _post.value = it.post
            _feed.value = it.feed
        }
    }
}