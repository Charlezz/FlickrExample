package com.sum3years.domain.repository

import com.sum3years.data.model.Photo
import kotlinx.coroutines.flow.Flow

interface FlickerRepository {
    suspend fun getPhotos(query: String, page: Int, pageSize: Int): Result<List<Photo>>
    fun loadDownloadedPhotos(): Flow<List<String>>
    suspend fun insertDownloadedPhoto(fireUri: String)
    suspend fun deleteDownloadedPhoto(fireUri: String)

    suspend fun insertSearchHistory(history: String)
    suspend fun deleteSearchHistory(history: String)
    fun loadSearchHistories(): Flow<List<String>>
}
