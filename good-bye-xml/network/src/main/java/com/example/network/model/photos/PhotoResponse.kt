package com.example.network.model.photos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PhotoResponse(
    val id: Long,
    val owner: String,
    val secret: String,
    val server: Int,
    val farm: Int,
    val title: String,
    @field:Json(name = "ispublic")
    val isPublic: Int,
    @field:Json(name = "isfriend")
    val isFriend: Int,
    @field:Json(name = "isfamily")
    val isFamily: Int
)