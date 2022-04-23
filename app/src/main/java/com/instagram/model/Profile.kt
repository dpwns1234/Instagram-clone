package com.instagram.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName

@IgnoreExtraProperties
data class Profile (
    val nickname: String,
    @SerializedName("profile_image") val profileImage: String?,
    @SerializedName("post_count") val postCount: Int,
    @SerializedName("follower_count") val followerCount: Int,
    @SerializedName("following_count") val followingCount: Int,
    val name: String,
    val introduce: String?,
    val posts: List<PreviewPost>?,
    @SerializedName("user_posts") val userPosts: List<PreviewPost>?
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nickname" to nickname,
            "profile_image" to profileImage,
            "follower_count" to postCount,
            "following_count" to followerCount,
            "profile_image" to followingCount,
            "name" to name,
            "introduce" to introduce,
            "posts" to posts,
            "user_posts" to userPosts
        )
    }
}

data class PreviewPost(
    val postIdx: Int,
    @SerializedName("post_image") val postImage: String
)

