package com.sum3years.domain.repository

import com.sum3years.data.model.Photo
import kotlinx.coroutines.flow.Flow

interface FlickerRepository {
    suspend fun getPhotos(query: String, page: Int, pageSize: Int): Result<List<Photo>>

    suspend fun insertSearchHistory(history: String)
    suspend fun deleteSearchHistory(history: String)
    fun loadSearchHistories(): Flow<List<String>>
}
