package com.instagram.repository.profile

import com.instagram.model.Profile

interface ProfileDataSource {
    suspend fun getProfileData(): Profile
}