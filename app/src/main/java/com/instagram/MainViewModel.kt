package com.instagram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.instagram.model.Post

class MainViewModel: ViewModel() {
    val _post = MutableLiveData<Post>()
    var post: LiveData<Post> = _post

    init {
        loadPost()
    }

    private fun loadPost() {
        // Main Repository에서 data 받기.
        _post.value
    }
}