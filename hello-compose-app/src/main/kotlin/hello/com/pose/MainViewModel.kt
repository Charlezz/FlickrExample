package hello.com.pose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.com.pose.shared.domain.photo.FetchPhotoListUseCase
import hello.com.pose.shared.domain.photo.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchPhotoListUseCase: FetchPhotoListUseCase,
    private val downloader: Downloader
) : StateViewModel<MainState>() {

    val photoList = mutableStateListOf<Photo>()

    private var page by mutableStateOf(1)
    var canPaginate by mutableStateOf(false)
    var pagingState by mutableStateOf(PagingState.IDLE)
    var prevQuery by mutableStateOf("")

    fun getPhotosByQuery(query: String) = viewModelScope.launch {
        if(query.isBlank()) return@launch
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

    override fun initState(): MainState = MainState()
}

enum class PagingState {
    IDLE,
    LOADING,
    PAGINATING,
    ERROR,
    PAGINATION_EXHAUST
}

data class MainState(
    val isLoading: Boolean = false,
    var isCompleted: Boolean = false
)