package good.bye.xml

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import good.bye.xml.domain.model.photo.Photo
import good.bye.xml.domain.usecase.GetPhotoForRecentUseCase
import good.bye.xml.domain.usecase.GetPhotoForSearchUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPhotoForRecentUseCase: GetPhotoForRecentUseCase,
    private val getPhotoForSearchUseCase: GetPhotoForSearchUseCase,
): ViewModel() {

    private val _searchKeyword = mutableStateOf("")
    val searchKeyword = _searchKeyword

    private val _searchedImages = MutableStateFlow<PagingData<Photo>>(PagingData.empty())
    val searchedImages = _searchedImages

    init {
        recent()
    }

    fun updateSearchKeyword(keyword: String) {
        _searchKeyword.value = keyword
    }

    fun recent() {
        viewModelScope.launch {
            getPhotoForRecentUseCase()
                .cachedIn(viewModelScope)
                .collect { _searchedImages.value = it }
        }
    }

    fun search(keyword: String) {
        viewModelScope.launch {
            getPhotoForSearchUseCase(keyword)
                .cachedIn(viewModelScope)
                .collect { _searchedImages.value = it }
        }
    }
}