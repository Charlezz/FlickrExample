package hello.com.pose.presentation.main.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import hello.com.pose.presentation.main.MainRoute
import hello.com.pose.shared.domain.photo.Photo

const val mainNavigationRoute = "main"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mainScreen(
    onNavigateSetting: () -> Unit,
    onNavigateDetail: (Photo) -> Unit,
) {
    composable(route = mainNavigationRoute) {
        MainRoute(
            navigateDetail = onNavigateDetail,
            navigateSetting = onNavigateSetting,
        )
    }
}