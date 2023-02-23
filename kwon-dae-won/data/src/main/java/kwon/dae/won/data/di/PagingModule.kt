package kwon.dae.won.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kwon.dae.won.data.room.PhotosDatabase
import kwon.dae.won.data.room.PhotosRemoteMediator
import kwon.dae.won.domain.model.Photo
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object PagingModule {

    @Qualifier
    @Retention
    annotation class RecentPhotosPager

    @OptIn(ExperimentalPagingApi::class)
    @RecentPhotosPager
    @ViewModelScoped
    @Provides
    fun provideRecentPhotosPager(
        photosDatabase: PhotosDatabase,
        photosRemoteMediator: PhotosRemoteMediator
    ): Pager<Int, Photo> = Pager(
        config = PagingConfig(
            pageSize = 60,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            photosDatabase.getPhotosDao().getPhotos()
        },
        remoteMediator = photosRemoteMediator
    )
}
