package com.instagram.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName
import kotlin.String

@IgnoreExtraProperties
data class Profile(
    @SerializedName("user_uid") @get:PropertyName("user_uid") @set:PropertyName("user_uid") var userUid: String = "",
    val nickname: String = "",
    @SerializedName("profile_image") @get:PropertyName("profile_image") @set:PropertyName("profile_image") var profileImage: String? = "",
    @SerializedName("post_count") @get:PropertyName("post_count") @set:PropertyName("post_count")  var postCount: Int = 0,
    @SerializedName("follower_count") @get:PropertyName("follower_count") @set:PropertyName("follower_count")  var followerCount: Int = 0,
    @SerializedName("following_count") @get:PropertyName("following_count") @set:PropertyName("following_count")  var followingCount: Int = 0,
    val name: String = "",
    val introduce: String? = ""
//    @get:PropertyName("posts") @set:PropertyName("posts")  var posts: List<PreviewPost>? = null,
//    @get:PropertyName("user_posts") @set:PropertyName("user_posts")  var userPosts: List<PreviewPost>? = null
    ) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "user_uid" to userUid,
            "nickname" to nickname,
            "profile_image" to profileImage,
            "post_count" to postCount,
            "follower_count" to followerCount,
            "following_count" to followingCount,
            "name" to name,
            "introduce" to introduce
//            "posts" to posts,
//            "user_posts" to userPosts
        )
    }

}

@IgnoreExtraProperties
data class PreviewPost(
    @SerializedName("post_uid") @get:PropertyName("post_uid") @set:PropertyName("post_uid") var postUid: String? = null,
    @SerializedName("post_image") @get:PropertyName("post_image") @set:PropertyName("post_image") var postImage: String = "",
    @SerializedName("created_at") @get:PropertyName("created_at") @set:PropertyName("created_at") var createdAt: Long = 0,
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "post_uid" to postUid,
            "post_image" to postImage,
            "created_at" to createdAt
        )
    }
}


