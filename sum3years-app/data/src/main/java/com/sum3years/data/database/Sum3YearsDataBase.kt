package com.sum3years.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sum3years.data.database.dao.DownloadedPhotoDao
import com.sum3years.data.database.dao.SearchHistoryDao
import com.sum3years.data.model.DownloadedPhotoEntity
import com.sum3years.data.model.SearchHistoryEntity

@Database(entities = [SearchHistoryEntity::class, DownloadedPhotoEntity::class], version = 2)
abstract class Sum3YearsDataBase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun downloadedPhotoDao(): DownloadedPhotoDao
}
