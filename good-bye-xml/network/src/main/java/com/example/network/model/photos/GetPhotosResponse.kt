package com.example.network.model.photos

data class GetPhotosResponse(
    val photos: PhotosResponse,
    val stat: String
)
