package com.sum3years.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sum3years.data.database.Sum3YearsDataBase
import com.sum3years.data.database.dao.DownloadedPhotoDao
import com.sum3years.data.database.dao.SearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideRoomDatabase(
        @ApplicationContext applicationContext: Context,
    ): Sum3YearsDataBase = Room.databaseBuilder(
        applicationContext,
        Sum3YearsDataBase::class.java,
        "sum3years_database",
    ).addMigrations(MIGRATION_1_2).build()

    val MIGRATION_1_2 = object : Migration(1,2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE `DownloadedPhotoTable` (`fileUri` TEXT NOT NULL, `created_at` INTEGER NOT NULL, PRIMARY KEY(`fileUri`))")
        }

    }

    @Provides
    fun provideSearchHistoryDao(db: Sum3YearsDataBase): SearchHistoryDao {
        return db.searchHistoryDao()
    }
    @Provides
    fun provideDownloadedPhotoDao(db: Sum3YearsDataBase): DownloadedPhotoDao {
        return db.downloadedPhotoDao()
    }
}
