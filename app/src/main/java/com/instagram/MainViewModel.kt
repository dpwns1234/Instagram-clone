package com.instagram

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

    private val _main = MutableLiveData<Main>()
    var main: LiveData<Main> = _main

    private val _postImages = MutableLiveData<List<Image>>()
    var postImages: LiveData<List<Image>> = _postImages

    init {
        loadMain()
    }

    fun loadPostImages(index: Int): List<Image>? {
        return _post.value?.get(index)?.postImages
    }

    private fun loadMain() {
        // Main Repository에서 data 받기.
        val mainData = repository.getAssetData()
        mainData?.let {
            _post.value = it.post
            _feed.value = it.feed

//            val postList = it.post
//            for (post in postList) {
//                _postImages.value = post.postImages
//            }

//            _postImages.value = it.post.component1().postImages
//                post.value?.component1()?.postImages

            var value: Post
            for (index in 0 until it.post.size) {
                value = post.value?.get(index)!!
            }
        }
    }
}