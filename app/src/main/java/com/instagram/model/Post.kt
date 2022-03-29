package com.instagram.model

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class Post(
    @SerializedName("post_idx") val postIdx: Int?,
    val nickname: String?,
    @SerializedName("post_image") val postImage: String?,// List<Image>,
    @SerializedName("post_introduce") val postIntroduce: String?,
)
