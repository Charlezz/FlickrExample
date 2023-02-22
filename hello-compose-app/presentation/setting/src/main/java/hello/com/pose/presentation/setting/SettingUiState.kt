package hello.com.pose.presentation.setting

import hello.com.pose.shared.domain.setting.Setting


sealed interface SettingUiState {
    object Loading : SettingUiState

    data class Success(val setting: Setting) : SettingUiState
}