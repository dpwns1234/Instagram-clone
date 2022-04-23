package com.instagram.repository.profile

import com.instagram.model.Profile

class ProfileRepository(private val profileRemoteDataSource: ProfileRemoteDataSource) {
    suspend fun getProfileData(): Profile {
        return profileRemoteDataSource.getProfileData()
    }
}