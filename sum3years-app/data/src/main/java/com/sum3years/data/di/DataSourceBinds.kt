package com.sum3years.data.di

import com.sum3years.data.datasource.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceBinds {
    @Binds
    abstract fun bindDataSource(
        dataSource: FlickerRemoteDataSource,
    ): FlickerDataSource

    @Binds
    abstract fun bindSearchHistoryDataSource(
        dataSource: SearchHistoryDataSourceImpl,
    ): SearchHistoryDataSource

    @Binds
    abstract fun bindDownloadedDataSource(
        dataSource: DownloadedPhotoDataSourceImpl
    ): DownloadedPhotoDataSource
}
