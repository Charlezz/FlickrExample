package hello.com.pose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import hello.com.pose.navigation.AppNavHost
import hello.com.pose.shared.domain.setting.AppTheme
import hello.com.pose.shared.domain.setting.DarkThemeConfig
import hello.com.pose.ui.system.theme.AppBackground
import hello.com.pose.ui.system.theme.AppTheme
import hello.com.pose.ui.system.theme.ThemeType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var setting: AppActivityUiState by mutableStateOf(AppActivityUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        setting = it
                    }
                    .collect()
            }
        }

        setContent {
            val systemUiController = rememberSystemUiController()
            val darkTheme = shouldUseDarkTheme(setting)
            val appTheme = shouldUseGalleryTheme(setting)

            LaunchedEffect(systemUiController, darkTheme) {
                systemUiController.systemBarsDarkContentEnabled = !darkTheme
            }

            AppTheme(
                darkTheme = darkTheme,
                themeType = appTheme.toThemeType()
            ) {
                AppBackground {
                    val navController = rememberAnimatedNavController()
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.fillMaxSize()
                    )
                    //MainScreen()
                }
            }
        }
    }
}


private fun AppTheme.toThemeType(): ThemeType {
    return when (this) {
        AppTheme.DEFAULT -> ThemeType.DEFAULT
        AppTheme.MONSTERA -> ThemeType.MONSTERA
        AppTheme.CARROT -> ThemeType.CARROT
    }
}
@Composable
private fun shouldUseGalleryTheme(
    uiState: AppActivityUiState,
) = when (uiState) {
    AppActivityUiState.Loading -> AppTheme.DEFAULT
    is AppActivityUiState.Success -> uiState.setting.appTheme
}

@Composable
private fun shouldUseDarkTheme(uiState: AppActivityUiState): Boolean = when (uiState) {
    AppActivityUiState.Loading -> isSystemInDarkTheme()
    is AppActivityUiState.Success -> when(uiState.setting.darkThemeConfig) {
        DarkThemeConfig.SYSTEM -> isSystemInDarkTheme()
        DarkThemeConfig.DARK -> true
        DarkThemeConfig.LIGHT -> false
    }
}