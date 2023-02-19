package kwon.dae.won

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

    init {
        getRecentPhotos()
    }

    private val _recentImage = MutableStateFlow<PagingData<Photo>>(PagingData.empty())
    val recentImage : StateFlow<PagingData<Photo>>
        get() = _recentImage.asStateFlow()

    @OptIn(ExperimentalPagingApi::class)
    private fun getRecentPhotos() = viewModelScope.launch {
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                photosDatabase.getPhotosDao().getPhotos()
            },
            remoteMediator = photosRemoteMediator
        ).flow.cachedIn(viewModelScope).collect {
            _recentImage.value = it
        }
    }


    // TODO : PhotoSize suffix 추가 by Daewon
    fun downloadPhoto(fileName: String, desc: String, url: String) =
        downloadUseCase(fileName, desc, url)

}
