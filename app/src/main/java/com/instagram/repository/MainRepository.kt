package com.instagram.repository

import com.instagram.model.Main

// TODO. udemy강의 보기: Repository면 remote데이터도 올 수 있으니까 생성자가 아닌 fun 매개변수로 MainAssetData를 만들어야 하지 않았을까??
class MainRepository(private val mainAssetData: MainAssetDataSource) {
    fun getAssetData(): Main? {
        return mainAssetData.getMainData()
    }
}