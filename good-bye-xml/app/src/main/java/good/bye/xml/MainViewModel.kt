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
    //굳이 비즈니스 로직(페이징데이터를 가져오는 공통 로직)을 두개로 나누는것보단 하나로 통합하는게 깔끔할것같음.
    private val getPhotoForRecentUseCase: GetPhotoForRecentUseCase,
    private val getPhotoForSearchUseCase: GetPhotoForSearchUseCase
) : ViewModel() {

    private val _searchKeyword = mutableStateOf("")
    val searchKeyword = _searchKeyword

    private val _searchedImages = MutableStateFlow<PagingData<Photo>>(PagingData.empty())
    val searchedImages = _searchedImages

    init {
        loadImages()
    }

    fun updateSearchKeyword(keyword: String) {
        _searchKeyword.value = keyword
    }

    fun loadImages() = viewModelScope.launch {
        val searchQuery = _searchKeyword.value
        if (searchQuery.isBlank()) {
            getPhotoForRecentUseCase()
        } else {
            getPhotoForSearchUseCase(searchQuery)
        }.cachedIn(viewModelScope).collect {
            _searchedImages.value = it
        }
    }
}
