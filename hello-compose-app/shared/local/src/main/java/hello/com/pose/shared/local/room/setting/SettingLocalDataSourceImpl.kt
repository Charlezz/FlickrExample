package hello.com.pose.shared.local.room.setting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import hello.com.pose.shared.data.setting.AppThemeData
import hello.com.pose.shared.data.setting.DarkThemeConfigData
import hello.com.pose.shared.data.setting.SettingData
import hello.com.pose.shared.data.setting.SettingLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingLocalDataSourceImpl @Inject constructor(
    private val userPreferences: DataStore<Preferences>
) : SettingLocalDataSource {
    override val settingData: Flow<SettingData> =
        userPreferences.data.map { preferences ->
            SettingData(
                darkThemeConfigData = preferences[darkThemeConfigKey].orEmpty()
                    .toDarkThemeConfigData(),
                appThemedata = preferences[appThemeKey].orEmpty()
                    .toAppThemeData()
            )
        }

    override suspend fun setDarkTheme(darkThemeConfig: DarkThemeConfigData) {
        userPreferences.edit { pref ->
            pref[darkThemeConfigKey] = darkThemeConfig.toStringType()
        }
    }

    override suspend fun setAppTheme(appTheme: AppThemeData) {
        userPreferences.edit { pref ->
            pref[appThemeKey] = appTheme.toStringType()
        }
    }

    private fun String.toDarkThemeConfigData() = when (this) {
        DARK_THEME_DARK -> DarkThemeConfigData.DARK
        DARK_THEME_LIGHT -> DarkThemeConfigData.LIGHT
        else -> DarkThemeConfigData.SYSTEM
    }

    private fun String.toAppThemeData() = when (this) {
        APP_THEME_MONSTERA -> AppThemeData.MONSTERA
        APP_THEME_CARROT -> AppThemeData.CARROT
        else -> AppThemeData.DEFAULT
    }

    private fun DarkThemeConfigData.toStringType() = when (this) {
        DarkThemeConfigData.DARK -> DARK_THEME_DARK
        DarkThemeConfigData.LIGHT -> DARK_THEME_LIGHT
        else -> DARK_THEME_SYSTEM
    }

    private fun AppThemeData.toStringType() = when (this) {
        AppThemeData.DEFAULT -> APP_THEME_DEFAULT
        AppThemeData.MONSTERA -> APP_THEME_MONSTERA
        AppThemeData.CARROT -> APP_THEME_CARROT
    }

    companion object {
        val darkThemeConfigKey = stringPreferencesKey("dark_theme_config")
        val appThemeKey = stringPreferencesKey("gallery_theme")

        const val APP_THEME_DEFAULT = "default"
        const val APP_THEME_CARROT = "carrot"
        const val APP_THEME_MONSTERA = "monstera"

        const val DARK_THEME_DARK = "dark"
        const val DARK_THEME_LIGHT = "light"
        const val DARK_THEME_SYSTEM = "system"
    }
}