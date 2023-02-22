package kwon.dae.won.ui

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.testcolortheme.ui.theme.FlickrColorThemeTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kwon.dae.won.Gallery.GalleryNavGraph
import kwon.dae.won.R
import kwon.dae.won.home.HomeNavGraph
import kwon.dae.won.home.homeNavGraph
import kwon.dae.won.setting.SettingsNavGraph


/**
 * @author Daewon on 10,February,2023
 *
 */
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class
)
@Composable
fun FlickrApp(
    windowSizeClass: WindowSizeClass,
    flickrAppScaffoldState: FlickrAppScaffoldState = rememberFlickrAppScaffoldState(),
) {
    FlickrColorThemeTheme {
        val usePersistentNavigationDrawer = windowSizeClass.usePersistentNavigationDrawer
        FlickrAppDrawer(
            flickrAppScaffoldState = flickrAppScaffoldState,
            showPermanently = usePersistentNavigationDrawer,
            drawerSheetContent = {
                DrawerSheetContent(
                    selectedDrawerItem = flickrAppScaffoldState.selectedDrawerItem,
                    onClickDrawerItem = flickrAppScaffoldState::navigate
                )
            }
        ) {
            AnimatedNavHost(
                modifier = Modifier,
                navController = flickrAppScaffoldState.navController,
                startDestination = HomeNavGraph.homeRoute,
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
                homeNavGraph(
                    onNavigationIconClick = flickrAppScaffoldState::onNavigationClick,
                    onBackIconClick = flickrAppScaffoldState::onBackIconClick
                )
            }
        }
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun FlickrAppDrawer(
    flickrAppScaffoldState: FlickrAppScaffoldState = rememberFlickrAppScaffoldState(),
    showPermanently: Boolean,
    drawerSheetContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit
) {
    if (showPermanently) {
        PermanentNavigationDrawer(
            drawerContent = { PermanentDrawerSheet { drawerSheetContent() } }
        ) {
            Box(
                modifier = Modifier.consumedWindowInsets(
                    WindowInsets.systemBars.only(WindowInsetsSides.Start)
                )
            ) {
                content()
            }
        }
    } else {
        val keyboardController = LocalSoftwareKeyboardController.current
        val drawerState = flickrAppScaffoldState.drawerState
        LaunchedEffect(drawerState.isAnimationRunning) {
            if (drawerState.isAnimationRunning && drawerState.isClosed) {
                keyboardController?.hide()
            }
        }
        BackHandler(enabled = drawerState.isOpen) {
            flickrAppScaffoldState.coroutineScope.launch {
                drawerState.close()
            }
        }
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = { ModalDrawerSheet { drawerSheetContent() } },
        ) {
            content()
        }
    }
}

private val WindowSizeClass.usePersistentNavigationDrawer: Boolean
    get() = when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> false
        WindowWidthSizeClass.Medium -> false
        WindowWidthSizeClass.Expanded -> true
        else -> false
    }

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun rememberFlickrAppScaffoldState(
    navController: NavHostController = rememberAnimatedNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
): FlickrAppScaffoldState {
    return remember(navController, coroutineScope, drawerState) {
        FlickrAppScaffoldState(
            coroutineScope,
            navController,
            drawerState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
class FlickrAppScaffoldState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val coroutineScope: CoroutineScope,
    val navController: NavHostController,
    val drawerState: DrawerState
) {
    fun navigate(drawerItem: DrawerItem) {
        navController.navigate(drawerItem.navRoute)
        coroutineScope.launch {
            drawerState.close()
        }
    }

    fun onNavigationClick() {
        coroutineScope.launch {
            drawerState.open()
        }
    }

    fun onBackIconClick() {
        navController.popBackStack()
    }

    private var _selectedDrawerItem: MutableState<DrawerItem?> = mutableStateOf(null)
    val selectedDrawerItem get() = _selectedDrawerItem.value

    init {
        coroutineScope.launch {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                _selectedDrawerItem.value = destination.route?.let { DrawerItem.ofOrNull(it) }
            }
        }
    }
}

enum class DrawerItem(
    @StringRes val titleStringRes: Int,
    @DrawableRes val icon: Int,
    val navRoute: String
) {
    Home(
        R.string.title_main,
        R.drawable.home_24,
        HomeNavGraph.homeRoute
    ),
    Gallery(
        R.string.title_gallery,
        R.drawable.image_24,
        GalleryNavGraph.galleryRoute
    ),
    Setting(
        R.string.title_setting,
        R.drawable.settings_24,
        SettingsNavGraph.settingsRoute
    );

    companion object {
        fun ofOrNull(route: String): DrawerItem? {
            return values().asSequence().firstOrNull { it.navRoute == route }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerSheetContent(
    selectedDrawerItem: DrawerItem?,
    onClickDrawerItem: (DrawerItem) -> Unit
) {
    Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.charlezz),
        contentDescription = null,
        modifier = Modifier.padding(12.dp, 12.dp, 12.dp, 0.dp)
    )
    Column(
        modifier = Modifier.verticalScroll(
            rememberScrollState()
        )
    ) {
        DrawerItem.values().forEach { drawerItem ->
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = drawerItem.icon),
                        contentDescription = null
                    )
                },
                label = {
                    Text(stringResource(drawerItem.titleStringRes))
                },
                selected = drawerItem == selectedDrawerItem,
                onClick = {
                    onClickDrawerItem(drawerItem)
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}
