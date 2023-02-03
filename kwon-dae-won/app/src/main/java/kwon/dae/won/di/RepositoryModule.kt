package kwon.dae.won.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kwon.dae.won.data.repository.FlickrRepositoryImpl
import kwon.dae.won.domain.repository.FlickrRepository

/**
 * @author soohwan.ok
 * @since
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindsFlickrRepository(repo:FlickrRepositoryImpl):FlickrRepository
}