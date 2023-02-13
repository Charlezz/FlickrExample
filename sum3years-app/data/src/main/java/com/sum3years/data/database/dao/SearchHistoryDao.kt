package com.sum3years.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sum3years.data.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: SearchHistoryEntity)

    @Query("DELETE FROM SearchHistoryTable WHERE history = :history")
    suspend fun deleteHistory(history: String)

    @Query("SELECT * FROM SearchHistoryTable ORDER BY created_at DESC")
    fun loadAllHistory(): Flow<List<SearchHistoryEntity>>
}
