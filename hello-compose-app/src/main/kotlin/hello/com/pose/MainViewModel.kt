package hello.com.pose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.com.pose.shared.domain.photo.FetchPhotoListUseCase
import hello.com.pose.shared.domain.photo.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchPhotoListUseCase: FetchPhotoListUseCase,
    private val downloader: Downloader
) : ViewModel() {

    val photoList = mutableStateListOf<Photo>()

    private var page by mutableStateOf(1)
    var canPaginate by mutableStateOf(false)
    var pagingState by mutableStateOf(PagingState.IDLE)
    var prevQuery by mutableStateOf("")

    private val currentSearchQuery = MutableStateFlow("")
    val searchQuery = currentSearchQuery.asStateFlow()

    private val _downloadStateFlow: MutableStateFlow<DownLoadState> = MutableStateFlow(DownLoadState())
    val downloadStateFlow: StateFlow<DownLoadState> = _downloadStateFlow.asStateFlow()

    var downloadState: DownLoadState
        set(value) {
            _downloadStateFlow.value = value
        }
        get() = _downloadStateFlow.value

    fun setNewQuery(query: String) {
        currentSearchQuery.value = query
    }

    init {
        searchQueryByFlow()
    }

    private fun searchQueryByFlow() {
        searchQuery.debounce(500L).onEach { query ->
            if(query.isNotBlank()) {
                getPhotosByQuery(query)
            }
        }.launchIn(viewModelScope)
    }

    fun getPhotosByQuery(query: String) = viewModelScope.launch {
        if (page == 1 || (page != 1 && canPaginate) && pagingState == PagingState.IDLE) {
            pagingState = if (page == 1) PagingState.LOADING else PagingState.PAGINATING

            if (prevQuery != query) {
                page = 1
            }

            val list = mutableListOf<Photo>()
            fetchPhotoListUseCase.invoke(query = query, page = page).asFlow()
                .collectLatest { photo ->
                    list.add(photo)
                }

            canPaginate = list.size == 20

            if (page == 1) {
                photoList.clear()
                photoList.addAll(list)
            } else {
                photoList.addAll(list)
            }

            pagingState = PagingState.IDLE

            if (canPaginate) {
                page++
            }
            prevQuery = query
        } else {
            pagingState = if (page == 1) PagingState.ERROR else PagingState.PAGINATION_EXHAUST
        }
    }

    fun saveFile(url: String, photo: Photo) = viewModelScope.launch {
        val fileName = "${photo.id}.jpg"
        updateState { copy(isLoading = true, isCompleted = false) }
        withContext(Dispatchers.IO) {
            downloader.download(url, fileName)
        }
        updateState { copy(isLoading = false, isCompleted = true) }
    }

    private fun updateState(block: DownLoadState.() -> DownLoadState) {
        downloadState = block(downloadState)
    }
}

enum class PagingState {
    IDLE,
    LOADING,
    PAGINATING,
    ERROR,
    PAGINATION_EXHAUST
}

data class DownLoadState(
    val isLoading: Boolean = false,
    var isCompleted: Boolean = false
)