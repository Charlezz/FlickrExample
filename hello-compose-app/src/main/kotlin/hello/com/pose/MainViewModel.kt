package hello.com.pose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.com.pose.shared.domain.photo.FetchPhotoListUseCase
import hello.com.pose.shared.domain.photo.Photo
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchPhotoListUseCase: FetchPhotoListUseCase
) : ViewModel() {


    val photos: MutableState<MainState> = mutableStateOf(MainState())
    fun getImageList(query: String) {
        viewModelScope.launch {
            val result = fetchPhotoListUseCase.invoke(query, page = 1)
            photos.value = MainState(data = result)
        }
    }
}

data class MainState(
    val isLoading: Boolean = false,
    val data: List<Photo> = emptyList(),
    val error: String = ""
)