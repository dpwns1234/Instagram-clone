package com.instagram.repository.profile

import com.instagram.model.Post
import com.instagram.model.PreviewPost
import com.instagram.model.Profile

interface ProfileDataSource {
    suspend fun getProfileData(userUid: String): Profile

    suspend fun getPosts(userUid: String): List<PreviewPost>
}