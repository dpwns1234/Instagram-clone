package com.instagram.model

import com.google.gson.annotations.SerializedName

data class Feed(
    val user: User,
    @SerializedName("is_seen") val isSeen: Boolean?
)
