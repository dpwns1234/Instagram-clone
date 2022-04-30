package com.instagram.model

import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName
import kotlin.String

data class Post(
    @SerializedName("post_idx") @get:PropertyName("post_uid") @set:PropertyName("post_uid") var postUid: String? = null,
    val writer: User,
    @SerializedName("post_images") @get:PropertyName("post_images") @set:PropertyName("post_images") var posts: List<Image>,
    @SerializedName("post_introduce") @get:PropertyName("post_introduce") @set:PropertyName("post_introduce") var postIntroduce: String? = null,
    @SerializedName("likeUser_list") @get:PropertyName("likeUser_list") @set:PropertyName("likeUser_list") var likeUserList: List<User>? = null,
    @SerializedName("comment_ist") @get:PropertyName("comment_ist") @set:PropertyName("comment_ist") var commentList: List<Comment>? = null,
    @SerializedName("like_count") @get:PropertyName("like_count") @set:PropertyName("like_count") var likeCount: Int = 0,
    @SerializedName("comment_count") @get:PropertyName("comment_count") @set:PropertyName("comment_count") var commentCount: Int = 0,
    @SerializedName("created_at") @get:PropertyName("created_at") @set:PropertyName("created_at") var createdAt: String, // 나중에 변수형 바꾸기
){
    fun toMap(): Map<String, Any?> {
        return mapOf("post_idx" to postUid,
            "writer" to writer,
            "post_images" to posts,
            "post_introduce" to postIntroduce,
            "likeUser_list" to likeUserList,
            "comment_ist" to commentList,
            "like_count" to likeCount,
            "comment_count" to commentCount,
            "created_at" to createdAt,

        )
    }
}

data class Comment(
    val user: User,
    val comment: String = "",
    val createdAt: String = "",
    val likeList: List<Like>? = null
)

data class Like(
    val user: User,
    val postIdx: Int,
    val likeStatus: Boolean
)

data class Image(
    @SerializedName("image_idx")val imageIdx: Int = 0,
    @SerializedName("image_url") val imageUrl: String = ""
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "image_idx" to imageIdx,
            "image_url" to imageUrl
        )
    }
}