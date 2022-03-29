package com.instagram.model

import com.google.gson.annotations.SerializedName

data class Main(
    val post: List<Post>,
    val feed: List<Feed>
)

