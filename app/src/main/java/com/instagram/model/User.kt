package com.instagram.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName

@IgnoreExtraProperties // 안되면 이거 지우고 ㄱㄱ
data class User(
    val id: String,
    val name: String?,
    val nickname: String,
    @SerializedName("image_url") val imageUrl: String?,
    val introduce: String?
){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "nickname" to nickname,
            "imageUrl" to imageUrl,
            "introduce" to introduce
        )
    }
}