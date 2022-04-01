package com.instagram

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.instagram.repository.MainAssetDataSource
import com.instagram.repository.MainRepository

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                val repository = MainRepository(MainAssetDataSource(AssetLoader(context)))
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ItemPostViewModel::class.java) -> {
                val repository = MainRepository(MainAssetDataSource(AssetLoader(context)))
                ItemPostViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}