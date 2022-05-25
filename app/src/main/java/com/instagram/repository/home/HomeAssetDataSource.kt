package com.instagram.repository.home

import com.google.gson.Gson
import com.instagram.AssetLoader
import com.instagram.model.Main
import com.instagram.model.Post
import com.instagram.network.ApiClient

class HomeAssetDataSource(private val assetLoader: AssetLoader, private val apiClient: ApiClient): HomeDataSource {
    private val gson = Gson()

    override fun getMainData(): Main? {
        return assetLoader.getJsonString("main.json").let { mainJsonString ->
            gson.fromJson(mainJsonString, Main::class.java)
        }
    }

    override suspend fun getPosts(): List<Post>? {
        return apiClient.getPosts()
    }
}