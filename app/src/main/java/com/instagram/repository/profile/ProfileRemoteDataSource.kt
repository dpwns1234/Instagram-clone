package com.instagram.repository.profile

import com.instagram.model.Profile
import com.instagram.network.ApiClient

class ProfileRemoteDataSource(private val api: ApiClient, private val uid: String): ProfileDataSource {
    override suspend fun getProfileData(): Profile {
        return api.getProfile(uid)
    }
}