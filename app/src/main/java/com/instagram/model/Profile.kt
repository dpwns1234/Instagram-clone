package com.instagram.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName

@IgnoreExtraProperties
data class Profile(
    val nickname: String = "",
    @SerializedName("profile_image") @get:PropertyName("profile_image") @set:PropertyName("profile_image")  var profileImage: String? = "",
    @SerializedName("post_count") @get:PropertyName("post_count") @set:PropertyName("post_count")  var postCount: Int = 0,
    @SerializedName("follower_count") @get:PropertyName("follower_count") @set:PropertyName("follower_count")  var followerCount: Int = 0,
    @SerializedName("following_count") @get:PropertyName("following_count") @set:PropertyName("following_count")  var followingCount: Int = 0,
    val name: String = "",
    val introduce: String? = "",
    val posts: List<PreviewPost>? = null,
    @SerializedName("user_posts") @get:PropertyName("user_posts") @set:PropertyName("user_posts") var userPosts: List<PreviewPost>? = null

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
    @SerializedName("post_image") @get:PropertyName("post_image") @set:PropertyName("post_image") var postImage: String
)


