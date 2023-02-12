package com.example.network.datasource

import com.example.network.model.photos.GetPhotosResponse
import com.example.network.model.NetworkResult

interface FlickrRemoteDataSource {

    suspend fun getPhotosForRecent(perPage: Int, page: Int): NetworkResult<GetPhotosResponse>

    suspend fun getPhotosForSearch(keyword: String, perPage: Int, page: Int): NetworkResult<GetPhotosResponse>

}