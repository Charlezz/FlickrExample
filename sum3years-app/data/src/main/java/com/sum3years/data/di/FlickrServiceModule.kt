package com.sum3years.data.di

import com.sum3years.data.network.FlickerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
internal object FlickrServiceModule {
    @Provides
    @Singleton
    fun provideFlikrService(): FlickerService{
        return FlickerService.flickerService
    }

}