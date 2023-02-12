package good.bye.xml.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import good.bye.xml.data.repository.FlickrRepositoryImpl
import good.bye.xml.domain.repository.FlickrRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsFlickrRepository(
        repository: FlickrRepositoryImpl
    ): FlickrRepository
}