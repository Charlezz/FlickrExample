package hello.com.pose.shared.data.setting

import kotlinx.coroutines.flow.Flow

interface SettingLocalDataSource {
    val settingData: Flow<SettingData>

    suspend fun setDarkTheme(darkThemeConfig: DarkThemeConfigData)

    suspend fun setAppTheme(appThemeData: AppThemeData)
}