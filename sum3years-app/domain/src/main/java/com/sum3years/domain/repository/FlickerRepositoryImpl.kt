package com.sum3years.domain.repository

import com.sum3years.data.datasource.FlickerDataSource
import com.sum3years.data.datasource.SearchHistoryDataSource
import com.sum3years.data.model.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FlickerRepositoryImpl @Inject constructor(
    private val flickerDataSource: FlickerDataSource,
    private val searchHistoryDataSource: SearchHistoryDataSource,
) : FlickerRepository {

    override suspend fun getPhotos(query: String, page: Int, pageSize: Int): Result<List<Photo>> {
        return flickerDataSource.getPhotos(query, page, pageSize)
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
