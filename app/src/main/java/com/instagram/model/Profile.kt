package com.instagram.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import kotlin.String

@IgnoreExtraProperties
data class Profile(
    val nickname: String = "",
    @get:PropertyName("profile_image") @set:PropertyName("profile_image") var profileImage: String? = "",
    @get:PropertyName("post_count") @set:PropertyName("post_count")  var postCount: Int = 0,
    @get:PropertyName("follower_count") @set:PropertyName("follower_count")  var followerCount: Int = 0,
    @get:PropertyName("following_count") @set:PropertyName("following_count")  var followingCount: Int = 0,
    val name: String = "",
    val introduce: String? = ""
//    @get:PropertyName("posts") @set:PropertyName("posts")  var posts: List<PreviewPost>? = null,
//    @get:PropertyName("user_posts") @set:PropertyName("user_posts")  var userPosts: List<PreviewPost>? = null
    ) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nickname" to nickname,
            "profile_image" to profileImage,
            "postCount" to postCount,
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
    @get:PropertyName("post_uid") @set:PropertyName("post_uid") var postUid: String? = null,
    @get:PropertyName("post_image") @set:PropertyName("post_image") var postImage: String = "",
    @get:PropertyName("created_at") @set:PropertyName("created_at") var createdAt: Long = 0,
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


