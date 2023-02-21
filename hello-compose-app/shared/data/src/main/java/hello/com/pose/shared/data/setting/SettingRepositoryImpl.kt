package hello.com.pose.shared.data.setting

import hello.com.pose.shared.domain.setting.AppTheme
import hello.com.pose.shared.domain.setting.DarkThemeConfig
import hello.com.pose.shared.domain.setting.Setting
import hello.com.pose.shared.domain.setting.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val localDataSource: SettingLocalDataSource
) : SettingRepository {

    override val setting: Flow<Setting> = localDataSource.settingData.map {
        Setting(
            darkThemeConfig = it.darkThemeConfigData.toDomain(),
            appTheme = it.appThemedata.toDomain()
        )
    }

    override suspend fun setDarkTheme(darkThemeConfig: DarkThemeConfig) {
        localDataSource.setDarkTheme(
            when (darkThemeConfig) {
                DarkThemeConfig.DARK -> DarkThemeConfigData.DARK
                DarkThemeConfig.LIGHT -> DarkThemeConfigData.LIGHT
                DarkThemeConfig.SYSTEM -> DarkThemeConfigData.SYSTEM
            }
        )
    }

    override suspend fun setAppTheme(appTheme: AppTheme) {
        localDataSource.setAppTheme(
            when (appTheme) {
                AppTheme.DEFAULT -> AppThemeData.DEFAULT
                AppTheme.MONSTERA -> AppThemeData.MONSTERA
                AppTheme.CARROT -> AppThemeData.CARROT
            }
        )
    }

    private fun DarkThemeConfigData.toDomain() = when (this) {
        DarkThemeConfigData.DARK -> DarkThemeConfig.DARK
        DarkThemeConfigData.LIGHT -> DarkThemeConfig.LIGHT
        DarkThemeConfigData.SYSTEM -> DarkThemeConfig.SYSTEM
    }

    private fun AppThemeData.toDomain() = when (this) {
        AppThemeData.DEFAULT -> AppTheme.DEFAULT
        AppThemeData.MONSTERA -> AppTheme.MONSTERA
        AppThemeData.CARROT -> AppTheme.CARROT
    }
}