package com.sum3years.di

import com.sum3years.domain.repository.FlickerRepository
import com.sum3years.domain.repository.FlickerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleBinds {
    @Binds
    abstract fun bindFlickrRepository(
        repository: FlickerRepositoryImpl
    ): FlickerRepository

}