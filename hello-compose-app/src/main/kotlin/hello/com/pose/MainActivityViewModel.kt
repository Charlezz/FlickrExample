package hello.com.pose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.com.pose.shared.domain.setting.Setting
import hello.com.pose.shared.domain.setting.SettingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    settingRepository: SettingRepository
) : ViewModel() {

    val uiState: StateFlow<AppActivityUiState> = settingRepository.setting.map {
        AppActivityUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = AppActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )
}
sealed interface AppActivityUiState {
    object Loading : AppActivityUiState
    data class Success(val setting: Setting) : AppActivityUiState
}