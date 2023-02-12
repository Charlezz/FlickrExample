package com.example.network.model.photos

data class PhotosResponse(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: Int,
    val photo: List<PhotoResponse>
)