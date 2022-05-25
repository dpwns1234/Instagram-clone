package com.instagram.repository.home

import com.instagram.model.Main
import com.instagram.model.Post

interface HomeDataSource {
    fun getMainData(): Main?

    suspend fun getPosts(): List<Post>?
}