package com.instagram.model

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class Post(
    @SerializedName("post_idx") val postIdx: Int = 0,
    val writer: User,
    @SerializedName("post_images") val postImages: List<Image>,
    @SerializedName("post_introduce") val postIntroduce: String? = null,
    val likeUserList: List<User>? = null,
    val commentList: List<Comment>? = null,
    @SerializedName("like_count") val likeCount: Int = 0,
    @SerializedName("comment_count") val commentCount: Int = 0,
    @SerializedName("created_at") val createdAt: String, // 나중에 변수형 바꾸기
)

data class Comment(
    val user: User,
    val comment: String,
    val createdAt: String,
    val likeList: List<Like>
)

data class Like(
    val user: User,
    val postIdx: Int,
    val likeStatus: Boolean
)

data class Image(
    @SerializedName("image_url") val imageUrl: String,
    val tags: List<String>?,
    val coWorker: String?
)