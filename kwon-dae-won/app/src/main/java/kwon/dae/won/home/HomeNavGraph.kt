package kwon.dae.won.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import kwon.dae.won.photo.PhotoDetailScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeNavGraph(
    onNavigationIconClick: () -> Unit,
    onPhotoShortClick: (String) -> Unit,
    onBackIconClick: () -> Unit,
) {
    composable(
        route = HomeNavGraph.homeRoute,
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
        }
    ) {
        HomeScreen(
            onNavigationIconClick = onNavigationIconClick,
            onPhotoShortClick =  onPhotoShortClick,
        )
    }

    composable(
        route = HomeNavGraph.homeDetailRoute("{url}"),
        arguments = listOf(
            navArgument("url") {
                type = NavType.StringType
            }
        ),
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
        }
    ) {
        val url = it.arguments?.getString("url") ?: ""
        PhotoDetailScreen(url)
    }
}

object HomeNavGraph {
    const val homeRoute = "home"

    fun homeDetailRoute(url: String) =
        "home/detail/$url"
}
