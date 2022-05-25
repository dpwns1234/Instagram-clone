package com.instagram.model

import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName
import kotlin.String

data class Post(
    @SerializedName("post_uid") @get:PropertyName("post_uid") @set:PropertyName("post_uid") var postUid: String? = null,
    val writer: User = User(),
    @SerializedName("post_images") @get:PropertyName("post_images") @set:PropertyName("post_images") var posts: List<Image>? = null,
    @SerializedName("post_introduce") @get:PropertyName("post_introduce") @set:PropertyName("post_introduce") var postIntroduce: String? = null,
    @SerializedName("like_user_list") @get:PropertyName("like_user_list") @set:PropertyName("like_user_list") var likeUserList: MutableList<String> = mutableListOf(),
    @SerializedName("comment_list") val comments: MutableList<Comment> = mutableListOf(), // SerializedName 나중에 제거(main.json 삭제하면서 같이)
    @SerializedName("like_count") @get:PropertyName("like_count") @set:PropertyName("like_count") var likeCount: Int = 0,
    @SerializedName("comment_count") @get:PropertyName("comment_count") @set:PropertyName("comment_count") var commentCount: Int = 0,
    @SerializedName("created_at") @get:PropertyName("created_at") @set:PropertyName("created_at") var createdAt: Long = 0, // 나중에 변수형 바꾸기
){
    fun toMap(): Map<String, Any?> {
        return mapOf("post_uid" to postUid,
            "writer" to writer,
            "post_images" to posts,
            "post_introduce" to postIntroduce,
            "likeUser_list" to likeUserList,
            "comments" to comments,
            "like_count" to likeCount,
            "comment_count" to commentCount,
            "created_at" to createdAt,

        )
    }
}

data class Comment(
    @get:PropertyName("comment_uid") @set:PropertyName("comment_uid") var commentUid: String = "",
    val user: User = User(),
    val comment: String = "",
    @get:PropertyName("created_at") @set:PropertyName("created_at") var createdAt: Long = 0,
    // val likeList: List<Like>? = null,
    @get:PropertyName("list_users") @set:PropertyName("list_users") var  likeUsers: MutableList<String> = mutableListOf()
)

data class Like(
    val user: User? = null,
    val postIdx: Int = 0,
    val likeStatus: Boolean = false
)

data class Image(
    @SerializedName("image_idx") @get:PropertyName("image_idx") @set:PropertyName("image_idx") var imageIdx: Int = 0,
    @SerializedName("image_url") @get:PropertyName("image_url") @set:PropertyName("image_url") var imageUrl: String = ""
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "image_idx" to imageIdx,
            "image_url" to imageUrl
        )
    }
}