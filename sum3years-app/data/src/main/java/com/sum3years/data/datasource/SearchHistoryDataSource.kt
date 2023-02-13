package com.sum3years.data.datasource

import kotlinx.coroutines.flow.Flow

interface SearchHistoryDataSource {
    fun loadSearchHistories(): Flow<List<String>>
    suspend fun deleteSearchHistory(history: String)
    suspend fun insertSearchHistory(history: String)
}
