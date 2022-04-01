package com.instagram.repository

import android.util.Log
import com.instagram.model.Image
import com.instagram.model.Main
import com.instagram.model.Post

// TODO. udemy강의 보기: Repository면 remote데이터도 올 수 있으니까 생성자가 아닌 fun 매개변수로 MainAssetData를 만들어야 하지 않았을까??
class MainRepository(private val mainAssetData: MainAssetDataSource) {
    fun getAssetData(): Main? {
        return mainAssetData.getMainData()
    }

    fun getAssetImagesData(): List<Image> {
        val mainData = getAssetData()
        val images = mutableListOf<Image>()
        mainData?.let {
            for(posts in it.post) {
                images.addAll(posts.postImages)
            }
        }
        Log.d("ImagesData", images.toString())
        Log.d("ImagesData", "안녕-----------------------------------")
        println(images.toString())
        return images
    }

    fun getAssetPostData(): Post {
        val mainData = getAssetData()
        mainData.post
    }
}