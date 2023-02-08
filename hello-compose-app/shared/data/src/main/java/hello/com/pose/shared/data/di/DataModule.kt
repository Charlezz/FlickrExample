package hello.com.pose.shared.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hello.com.pose.shared.data.photo.PhotoRepositoryImpl
import hello.com.pose.shared.domain.photo.PhotoRepository

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataModuleBinds {
    @Binds
    abstract fun bindPhotoRepository(
        repository: PhotoRepositoryImpl
    ): PhotoRepository
}