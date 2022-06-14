package com.instagram.repository.profile

import com.instagram.model.PreviewPost
import com.instagram.model.Profile
import com.instagram.network.ApiClient
import retrofit2.Response

class ProfileRemoteDataSource(private val apiClient: ApiClient): ProfileDataSource {
    override suspend fun getProfileData(userUid: String): Profile {
        return apiClient.getProfile(userUid)
    }

    override suspend fun getPreviewPosts(userUid: String): List<PreviewPost> {
        return apiClient.getPreviewPosts(userUid)
    }

    override suspend fun getFollowingList(userUid: String): Response<MutableList<String>> {
        return apiClient.getFollowingList(userUid)
    }


}