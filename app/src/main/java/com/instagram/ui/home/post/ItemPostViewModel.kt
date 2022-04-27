package com.instagram.ui.home.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.instagram.model.Image


class ItemPostViewModel(private val imagesData: List<Image>): ViewModel() {
    private val _postImages = MutableLiveData<List<Image>>()
    var postImages: LiveData<List<Image>> = _postImages

    init {
        loadPostImages()
    }

    private fun loadPostImages() {
        imagesData.let { images ->
            _postImages.value = images
        }
    }
}