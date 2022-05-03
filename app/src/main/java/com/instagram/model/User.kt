package com.instagram.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import kotlin.String

@IgnoreExtraProperties // 안되면 이거 지우고 ㄱㄱ
data class User(
    val userUid: String = "",
    val name: String? = null,
    val nickname: String = "",
    @SerializedName("image_url") val imageUrl: String? = null,
    val introduce: String? = null
){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to userUid,
            "name" to name,
            "nickname" to nickname,
            "image_url" to imageUrl,
            "introduce" to introduce
        )
    }
}