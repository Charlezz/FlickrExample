package hello.com.pose.presentation.setting.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import hello.com.pose.presentation.setting.SettingScreen


const val settingNavigationRoute = "setting"

fun NavController.navigateSetting() {
    this.navigate(settingNavigationRoute)
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingScreen(
    onNavigateBack: () -> Unit
) {
    composable(route = settingNavigationRoute) {
        SettingScreen(
            onNavigateBack = onNavigateBack,
        )
    }
}