package com.instagram.model

import java.sql.Date

data class Post(
    val writer: Feed,
    val postImage: List<String>,
    val postIntroduce: String,
    val heartStatus: Boolean,
    val commentStatus: Boolean,
    val bookmarkStatus: Boolean,
    val heartCount: Int,
    val commentCount: Int,
    val comments: List<Comment>,
    val createdAt: Date,
    
    val isRead: Boolean // 해당 게시물을 읽었는지
)

data class Comment(
    val user: Feed,
    val comment: String,
    val createdAt: Date,
    val likeList: List<Like>
)

data class Like(
    val user: Feed,
    val name: String,
    val followStatus: Boolean
)