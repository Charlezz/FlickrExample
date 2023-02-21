package com.sum3years.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sum3years.data.model.DownloadedPhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadedPhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownloadedPhoto(downloadedPhoto: DownloadedPhotoEntity)

    @Query("DELETE FROM DownloadedPhotoTable WHERE fileUri = :fileUri")
    suspend fun deleteDownloadedPhoto(fileUri: String)

    @Query("SELECT * FROM DownloadedPhotoTable ORDER BY created_at DESC")
    fun loadDownloadedPhoto(): Flow<List<DownloadedPhotoEntity>>
}
