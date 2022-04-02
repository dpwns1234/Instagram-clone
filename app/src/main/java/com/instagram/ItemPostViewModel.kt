package com.instagram

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.instagram.model.Image
import com.instagram.repository.MainRepository


// 받아온 걸 못 사용한다면 그럼 그냥 첨부터 따로 받아오면 되지 않을까?
class ItemPostViewModel(private val imagesData: List<Image>): ViewModel() {
    private val _postImages = MutableLiveData<List<Image>>()
    var postImages: LiveData<List<Image>> = _postImages

    init {
        loadPostImages()
    }

    fun loadPostImages() {
        imagesData.let { images ->
            _postImages.value = images
            Log.d("ImagesData", "PostViewModel의 loadPostImage 함수 호출")
        }
    }
}