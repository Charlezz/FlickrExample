package hello.com.pose.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import hello.com.pose.presentation.detail.navigation.detailScreen
import hello.com.pose.presentation.detail.navigation.navigateDetail
import hello.com.pose.presentation.main.navigation.mainNavigationRoute
import hello.com.pose.presentation.main.navigation.mainScreen
import hello.com.pose.presentation.setting.navigation.navigateSetting
import hello.com.pose.presentation.setting.navigation.settingScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = mainNavigationRoute
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { slideInFromRight() },
    ) {
        mainScreen(
            onNavigateDetail = { photo -> navController.navigateDetail(photo.getStaticImageUrl(), photo.title) },
            onNavigateSetting = { navController.navigateSetting() }
        )
        detailScreen(
            onNavigateBack = navController::popBackStack,
        )
        settingScreen(
            onNavigateBack = navController::popBackStack,
        )
    }
}