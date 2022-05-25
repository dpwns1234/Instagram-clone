package com.instagram.repository.home

import com.instagram.model.Main
import com.instagram.model.Post

// TODO. udemy강의 보기: Repository면 remote데이터도 올 수 있으니까 생성자가 아닌 fun 매개변수로 MainAssetData를 만들어야 하지 않았을까??
class HomeRepository(private val homeAssetData: HomeAssetDataSource) {
    fun getAssetData(): Main? {
        return homeAssetData.getMainData()
    }

    suspend fun getPosts(): List<Post>? {
        return homeAssetData.getPosts()
    }

//    fun getAssetImagesData(): List<Image> {
//        val mainData = getAssetData()
//        val images = mutableListOf<Image>()
//        mainData?.let {
//            for(post in it.post) {
//                images.addAll(post.postImages)
//            }
//        }
//        return images
//    }

}