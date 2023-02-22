package good.bye.xml.local.room.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import good.bye.xml.local.room.datasource.FlickrLocalDataSource
import good.bye.xml.local.room.datasource.FlickrLocalDataSourceImpl


@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    abstract fun bindFlickrLocalDataSource(
        remote: FlickrLocalDataSourceImpl
    ): FlickrLocalDataSource
}