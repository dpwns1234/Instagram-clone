package com.instagram.model

import com.google.gson.annotations.SerializedName

data class User(
    val name: String?,
    val nickname: String,
    @SerializedName("image_url") val imageUrl: String?,
    val introduce: String?
)
