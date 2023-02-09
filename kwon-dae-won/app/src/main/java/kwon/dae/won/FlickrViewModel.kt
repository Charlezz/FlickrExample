package kwon.dae.won

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kwon.dae.won.data.retrofit.FlickrRetrofitFactory
import kwon.dae.won.data.room.PhotosDatabase
import kwon.dae.won.data.room.PhotosRemoteMediator
import kwon.dae.won.data.usecase.DownloadUseCase
import kwon.dae.won.domain.model.Photo
import kwon.dae.won.domain.repository.FlickrRepository
import kwon.dae.won.domain.usecase.GetRecentUseCase
import timber.log.Timber
import javax.inject.Inject

const val PAGE_SIZE = 100
@HiltViewModel
class FlickrViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getRecentUseCase: GetRecentUseCase,
    private val downloadUseCase: DownloadUseCase,
    private val flickrRepository: FlickrRepository,
    private val photosRemoteMediator: PhotosRemoteMediator,
    private val photosDatabase: PhotosDatabase,
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    fun getRecentPhotos(): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                photosDatabase.getPhotosDao().getPhotos()
            },
            remoteMediator = PhotosRemoteMediator(
                flickrRepository,
                photosDatabase,
            )
        ).flow
}
