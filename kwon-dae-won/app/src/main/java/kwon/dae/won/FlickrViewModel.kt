package kwon.dae.won

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kwon.dae.won.data.room.PhotosDatabase
import kwon.dae.won.data.room.PhotosRemoteMediator
import kwon.dae.won.data.usecase.DownloadUseCase
import kwon.dae.won.domain.model.Photo
import kwon.dae.won.domain.usecase.GetRecentUseCase
import javax.inject.Inject

const val PAGE_SIZE = 60

@HiltViewModel
class FlickrViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getRecentUseCase: GetRecentUseCase,
    private val downloadUseCase: DownloadUseCase,
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
            remoteMediator = photosRemoteMediator
        ).flow

    // TODO : PhotoSize suffix 추가 by Daewon
    fun downloadPhoto(fileName: String, desc: String, url: String) =
        downloadUseCase(fileName, desc, url)

}
