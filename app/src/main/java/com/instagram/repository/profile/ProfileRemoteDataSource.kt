package com.instagram.repository.profile

import com.instagram.model.Post
import com.instagram.model.PreviewPost
import com.instagram.model.Profile
import com.instagram.network.ApiClient

class ProfileRemoteDataSource(private val apiClient: ApiClient): ProfileDataSource {
    override suspend fun getProfileData(userUid: String): Profile {
        return apiClient.getProfile(userUid)
    }

    override suspend fun getPosts(userUid: String): List<PreviewPost> {
        return apiClient.getPosts(userUid)
    }
}