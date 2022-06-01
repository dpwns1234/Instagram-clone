package com.instagram.repository.profile

import com.instagram.model.Post
import com.instagram.model.PreviewPost
import com.instagram.model.Profile
import retrofit2.Response

interface ProfileDataSource {
    suspend fun getProfileData(userUid: String): Profile

    suspend fun getPreviewPosts(userUid: String): List<PreviewPost>

    suspend fun getFollowingList(userUid: String): Response<List<String>>
}