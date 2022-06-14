package com.instagram.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.instagram.AssetLoader
import com.instagram.network.ApiClient
import com.instagram.repository.home.HomeAssetDataSource
import com.instagram.repository.home.HomeRepository
import com.instagram.repository.profile.ProfileRemoteDataSource
import com.instagram.repository.profile.ProfileRepository
import com.instagram.ui.home.HomeViewModel
import com.instagram.ui.profile.ProfileViewModel
import com.instagram.ui.userprofile.UserProfileViewModel

class ViewModelFactory(private val context: Context, private val userUid: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                val repository = HomeRepository(HomeAssetDataSource(AssetLoader(context), ApiClient.create()))
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                val repository = ProfileRepository(ProfileRemoteDataSource(ApiClient.create()))
                ProfileViewModel(repository, userUid) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}