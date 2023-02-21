package com.example.network.model.photos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotosResponse(
    val page: Int,
    @field:Json(name = "pages")
    val totalPages: Int,
    val perpage: Int,
    val total: Int,
    val photo: List<PhotoResponse>
)