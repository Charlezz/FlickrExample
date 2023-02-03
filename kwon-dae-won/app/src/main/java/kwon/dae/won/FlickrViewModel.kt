package kwon.dae.won

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kwon.dae.won.domain.usecase.GetRecentUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FlickrViewModel @Inject constructor(
    savedStateHandle:SavedStateHandle,
    private val getRecentUseCase: GetRecentUseCase,
) : ViewModel(){
    init {
        데이터_잘불러와지는지_테스트()
    }

    private fun 데이터_잘불러와지는지_테스트(){
        viewModelScope.launch {
            val recent = getRecentUseCase()
            Timber.d("recent = $recent")
        }
    }
}