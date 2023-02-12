package good.bye.xml.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import good.bye.xml.data.remote.FlickrRemoteDataSource
import good.bye.xml.data.remote.FlickrRemoteDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    abstract fun bindsFlickrRemoteDataSource(
        remote: FlickrRemoteDataSourceImpl
    ): FlickrRemoteDataSource
}