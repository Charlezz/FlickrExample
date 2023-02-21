package com.sum3years.data.datasource

import com.sum3years.data.model.Photo
import kotlinx.coroutines.flow.Flow


interface DownloadedPhotoDataSource {
    fun loadDownloadedPhotos(): Flow<List<String>>
    suspend fun deleteDownloadedPhoto(fileUri: String)
    suspend fun insertDownloadedPhoto(fileUri: String)
}
