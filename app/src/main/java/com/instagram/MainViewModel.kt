package com.instagram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.instagram.model.Feed
import com.instagram.model.Main
import com.instagram.model.Post
import com.instagram.repository.MainRepository

class MainViewModel(private val repository: MainRepository): ViewModel() {
    private val _post = MutableLiveData<Post?>()
    var post: LiveData<Post?> = _post

    private val _feed = MutableLiveData<Feed>()
    var feed: LiveData<Feed> = _feed

    private val _main = MutableLiveData<Main>()
    var main: LiveData<Main> = _main

    init {
        loadMain()
    }

    private fun loadMain() {
        // Main Repository에서 data 받기.
        _main.value = repository.getAssetData()
    }
}