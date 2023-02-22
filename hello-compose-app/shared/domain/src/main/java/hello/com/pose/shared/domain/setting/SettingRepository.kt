package hello.com.pose.shared.domain.setting

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    val setting: Flow<Setting>

    suspend fun setDarkTheme(darkThemeConfig: DarkThemeConfig)

    suspend fun setAppTheme(appTheme: AppTheme)

}