package hello.com.pose.ui.system.component

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(
    @StringRes titleRes: Int,
    navigateIcon: NavigationNavigateIcon = NavigationNavigateIcon.BACK,
    onNavigateClick: () -> Unit = { },
) {
    TopNavigationBar(
        title = stringResource(id = titleRes),
        navigateIcon = navigateIcon,
        onNavigateClick = onNavigateClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(
    title: String,
    navigateIcon: NavigationNavigateIcon = NavigationNavigateIcon.BACK,
    onNavigateClick: () -> Unit = { },
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent
    )
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onNavigateClick) {
                Icon(
                    imageVector = when (navigateIcon) {
                        NavigationNavigateIcon.BACK -> Icons.Rounded.ArrowBack
                        NavigationNavigateIcon.CLOSE -> Icons.Rounded.Close
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopCenterNavigationBar(
    @StringRes titleRes: Int,
    navigationIcon: ImageVector? = null,
    actionIcon: ImageVector? = null,
    onNavigationClick: () -> Unit = { },
    onActionClick: () -> Unit = { }
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        navigationIcon = {
            navigationIcon?.let { vector ->
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = vector,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        actions = {
            actionIcon?.let { vector ->
                IconButton(onClick = onActionClick) {
                    Icon(
                        imageVector = vector,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    )
}

enum class NavigationNavigateIcon {
    BACK, CLOSE
}