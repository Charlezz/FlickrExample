package com.sum3years.data.datasource

import com.sum3years.data.database.dao.DownloadedPhotoDao
import com.sum3years.data.database.dao.SearchHistoryDao
import com.sum3years.data.model.DownloadedPhotoEntity
import com.sum3years.data.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchHistoryDataSourceImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao,
) : SearchHistoryDataSource {

    override fun loadSearchHistories(): Flow<List<String>> {
        return searchHistoryDao.loadAllHistory().map { histories ->
            histories.map { history -> history.history }
        }
    }

    override suspend fun deleteSearchHistory(history: String) {
        searchHistoryDao.deleteHistory(history)
    }

    override suspend fun insertSearchHistory(history: String) {
        searchHistoryDao.insertHistory(
            SearchHistoryEntity(history, System.currentTimeMillis()),
        )
    }
}
