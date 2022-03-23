package com.instagram.model

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class Post(
    @SerializedName("post_idx") val postIdx: Int,
    val writer: User,
    @SerializedName("post_images") val postImage: List<Image>,
    @SerializedName("post_introduce") val postIntroduce: String,
    @SerializedName("like_status") val likeStatus: Boolean,
    @SerializedName("comment_status") val commentStatus: Boolean,
    @SerializedName("bookmark_status") val bookmarkStatus: Boolean,
    @SerializedName("like_count") val likeCount: Int,
    @SerializedName("comment_count") val commentCount: Int,
    val comments: List<Comment>,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("is_read") val isRead: Boolean // 해당 게시물을 읽었는지
)

data class Comment(
    val user: User,
    val comment: String,
    val createdAt: Date,
    val likeList: List<Like>
)

data class Like(
    val user: User,
    val name: String,
    val followStatus: Boolean
)

data class Image(
    val imageUrl: String,
    val tags: List<String>,
    val coWorker: String
)