package com.sum3years.data.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SearchHistoryDataSource {
    fun loadSearchHistories(): Flow<List<String>>
    suspend fun deleteSearchHistory(history: String)
    suspend fun insertSearchHistory(history: String)
}
