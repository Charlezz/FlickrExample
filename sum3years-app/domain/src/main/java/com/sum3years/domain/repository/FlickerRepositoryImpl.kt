package com.sum3years.domain.repository

import com.sum3years.data.datasource.DownloadedPhotoDataSource
import com.sum3years.data.datasource.FlickerDataSource
import com.sum3years.data.datasource.SearchHistoryDataSource
import com.sum3years.data.model.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FlickerRepositoryImpl @Inject constructor(
    private val flickerDataSource: FlickerDataSource,
    private val downloadedPhotoDataSource: DownloadedPhotoDataSource,
    private val searchHistoryDataSource: SearchHistoryDataSource,
) : FlickerRepository {

    override suspend fun getPhotos(query: String, page: Int, pageSize: Int): Result<List<Photo>> {
        return flickerDataSource.getPhotos(query, page, pageSize)
    }

    override fun loadDownloadedPhotos(): Flow<List<String>> {
        return downloadedPhotoDataSource.loadDownloadedPhotos()
    }
    override suspend fun insertDownloadedPhoto(fileUri: String) {
        downloadedPhotoDataSource.insertDownloadedPhoto(fileUri)
    }

    override suspend fun deleteDownloadedPhoto(fireUri: String) {
        downloadedPhotoDataSource.deleteDownloadedPhoto(fireUri)
    }

    override suspend fun insertSearchHistory(history: String) {
        searchHistoryDataSource.insertSearchHistory(history)
    }

    override suspend fun deleteSearchHistory(history: String) {
        searchHistoryDataSource.deleteSearchHistory(history)
    }

    override fun loadSearchHistories(): Flow<List<String>> {
        return searchHistoryDataSource.loadSearchHistories()
    }
}
