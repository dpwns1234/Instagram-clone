package com.instagram.model

import com.google.gson.annotations.SerializedName

data class Profile (
    val nickname: String,
    @SerializedName("profile_image") val profileImage: String,
    @SerializedName("post_count") val postCount: Int,
    @SerializedName("follower_count") val followerCount: Int,
    @SerializedName("following_count") val followingCount: Int,
    val name: String,
    val introduce: String,
    val posts: List<PreviewPost>,
    @SerializedName("user_posts") val userPosts: List<PreviewPost>
)

data class PreviewPost(
    val postIdx: Int,
    @SerializedName("post_image") val postImage: String
)