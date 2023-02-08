package hello.com.pose.shared.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hello.com.pose.shared.data.photo.PhotoRemoteDataSource
import hello.com.pose.shared.remote.photo.PhotoRemoteDataSourceImpl


@InstallIn(SingletonComponent::class)
@Module
internal abstract class RemoteDataBindsModule {

    @Binds
    abstract fun bindPhotoRemoteDataSource(dataSource: PhotoRemoteDataSourceImpl): PhotoRemoteDataSource
}