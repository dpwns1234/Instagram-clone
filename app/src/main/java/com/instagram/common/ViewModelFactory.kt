package com.instagram.common

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.instagram.AssetLoader
import com.instagram.network.ApiClient
import com.instagram.repository.MainAssetDataSource
import com.instagram.repository.MainRepository
import com.instagram.repository.profile.ProfileRemoteDataSource
import com.instagram.repository.profile.ProfileRepository
import com.instagram.ui.home.HomeViewModel
import com.instagram.ui.profile.ProfileViewModel

class ViewModelFactory(private val context: Context, private val userUid: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                val repository = MainRepository(MainAssetDataSource(AssetLoader(context)))
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