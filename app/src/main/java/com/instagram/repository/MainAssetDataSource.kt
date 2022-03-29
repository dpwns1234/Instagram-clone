package com.instagram.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.instagram.AssetLoader
import com.instagram.model.Main

class MainAssetDataSource(private val assetLoader: AssetLoader): MainDataSource {
    private val gson = Gson()

    override fun getMainData(): Main? {
        return assetLoader.getJsonString("main.json").let { mainJsonString ->
            gson.fromJson(mainJsonString, Main::class.java)
        }
    }
}