package hello.com.pose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavBackStackEntry

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.slideInFromRight() =
    slideIntoContainer(AnimatedContentScope.SlideDirection.Left)

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.slideOutToRight() =
    slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)