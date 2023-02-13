package com.sum3years.data.di

import android.content.Context
import androidx.room.Room
import com.sum3years.data.database.Sum3YearsDataBase
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
    ).build()

    @Provides
    fun provideSearchHistoryDao(db: Sum3YearsDataBase): SearchHistoryDao {
        return db.searchHistoryDao()
    }
}
