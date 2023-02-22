package com.example.network.di

import com.example.network.datasource.FlickrRemoteDataSource
import com.example.network.datasource.FlickrRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    abstract fun bindFlickrRemoteDataSource(
        remote: FlickrRemoteDataSourceImpl
    ): FlickrRemoteDataSource
}