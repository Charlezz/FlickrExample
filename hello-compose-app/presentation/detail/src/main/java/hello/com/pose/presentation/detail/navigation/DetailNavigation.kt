package hello.com.pose.presentation.detail.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import hello.com.pose.presentation.detail.PhotoDetailRoute
import hello.com.pose.presentation.detail.navigation.Detail.KEY_TITLE
import hello.com.pose.presentation.detail.navigation.Detail.KEY_URL

fun NavController.navigateDetail(
    url: String,
    title: String
) {
    navigate("${Detail.route}?$KEY_URL=$url&$KEY_TITLE=$title")
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.detailScreen(
    onNavigateBack: () -> Unit
) {
    composable(
        route = Detail.routeArgs,
        arguments = Detail.arguments,
    ) { backStackEntry ->
        val url = backStackEntry.arguments?.getString(KEY_URL).orEmpty()
        val title = backStackEntry.arguments?.getString(KEY_TITLE).orEmpty()

        PhotoDetailRoute(url, title, onNavigateBack)
    }
}

internal object Detail {
    internal const val KEY_URL = "url"
    internal const val KEY_TITLE = "title"
    internal const val route: String = "detail"
    internal const val routeArgs = "$route?$KEY_URL={$KEY_URL}&$KEY_TITLE={$KEY_TITLE}"

    internal val arguments = listOf(
        navArgument(KEY_URL) {
            type = NavType.StringType
        },
        navArgument(KEY_TITLE) {
            type = NavType.StringType
        }
    )
}

