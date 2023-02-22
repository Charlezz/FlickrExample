package com.example.network.datasource

import com.example.network.api.FlickrApi
import com.example.network.model.NetworkResult
import com.example.network.model.photos.GetPhotosResponse
import javax.inject.Inject

class FlickrRemoteDataSourceImpl @Inject constructor(
    val flickrApi: FlickrApi
): FlickrRemoteDataSource {

    override suspend fun getPhotosForRecent(perPage: Int, page: Int): NetworkResult<GetPhotosResponse> {
        return flickrApi.getPhotosForRecent(perPage, page)
    }

    override suspend fun getPhotosForSearch(keyword: String, perPage: Int, page: Int): NetworkResult<GetPhotosResponse> {
        return flickrApi.getPhotosForSearch(keyword, perPage, page)
    }

}