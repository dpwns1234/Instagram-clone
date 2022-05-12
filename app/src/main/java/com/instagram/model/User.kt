package com.instagram.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName
import kotlin.String

@IgnoreExtraProperties
data class User(
    @get:PropertyName("user_uid") @set:PropertyName("user_uid") @SerializedName("user_uid") var userUid: String = "",
    val name: String? = null,
    val nickname: String = "",
    @get:PropertyName("profile_image") @set:PropertyName("profile_image") @SerializedName("profile_image") var profileImage: String? = null,
    val introduce: String? = null
){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "user_uid" to userUid,
            "name" to name,
            "nickname" to nickname,
            "profile_image" to profileImage,
            "introduce" to introduce
        )
    }
}