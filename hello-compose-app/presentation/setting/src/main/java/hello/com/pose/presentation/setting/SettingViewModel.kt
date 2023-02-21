package hello.com.pose.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.com.pose.shared.domain.setting.AppTheme
import hello.com.pose.shared.domain.setting.DarkThemeConfig
import hello.com.pose.shared.domain.setting.SettingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository
) : ViewModel() {

    val settingsUiState: StateFlow<SettingUiState> =
        settingRepository.setting
            .map { setting ->
                SettingUiState.Success(
                    setting = setting
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = SettingUiState.Loading
            )


    fun updateDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            settingRepository.setDarkTheme(darkThemeConfig)
        }
    }

    fun updateAppTheme(appTheme: AppTheme) {
        viewModelScope.launch {
            settingRepository.setAppTheme(appTheme)
        }
    }
}

data class ThemeSetting(val darkThemeConfig: DarkThemeConfig)