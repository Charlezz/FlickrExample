package hello.com.pose.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import hello.com.pose.shared.domain.photo.Photo
import hello.com.pose.ui.system.component.IconSetting
import hello.com.pose.ui.system.component.SearchBar
import kotlinx.coroutines.launch
import androidx.paging.compose.collectAsLazyPagingItems as collectAsLazyPagingItems1

@Composable
internal fun MainRoute(
    navigateDetail: (Photo) -> Unit,
    navigateSetting: () -> Unit,
    mainViewModel: MainScreenViewModel = hiltViewModel()
) {
    val state = mainViewModel.stateFlow.collectAsState().value

    MainScreen(
        onClickPhoto = navigateDetail,
        onSearch = { mainViewModel.eventHandler(MainContract.Event.DoSearchPhoto(it)) },
        onClickSetting = navigateSetting,
        state = state
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun MainScreen(
    onClickPhoto: (Photo) -> Unit,
    onSearch: (String) -> Unit,
    onClickSetting: () -> Unit,
    state: MainContract.State
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PhotoList(
            state = state.photoListState,
            onClickPhoto = onClickPhoto,
            onSearch = {
                onSearch(it)
                keyboardController?.hide()
            },
            onClickSetting = onClickSetting
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoList(
    state: MainContract.State.PhotoListState,
    onClickPhoto: (Photo) -> Unit,
    onSearch: (String) -> Unit,
    onClickSetting: () -> Unit
) {
    val scrollState = rememberLazyGridState()

    val pagingItems = if (state is MainContract.State.PhotoListState.Success)
        state.pagingDataFlow.collectAsLazyPagingItems1()
    else null

    val scope = rememberCoroutineScope()

    Scaffold(floatingActionButton = {
        AnimatedVisibility(visible = !scrollState.isScrollingUp(), enter = fadeIn(), exit = fadeOut()) {
            ScrollToTop {
                scope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }
        }
    }) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(PhotoCellCount),
            modifier = Modifier
                .padding(paddingValues = PaddingValues(8.dp)),
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            searchBar(onSearch, onClickSetting)
            pagingItems?.let { pagingItems ->
                items(
                    count = pagingItems.itemCount
                ) { index ->
                    pagingItems[index]?.let { photo ->
                        PhotoSquare(photo, onClickPhoto)
                    }
                }

                when {
                    pagingItems.loadState.refresh is LoadState.Loading -> loadingItem()
                    pagingItems.loadState.append is LoadState.Loading -> loadingItem()
                }
            }
        }
    }
}

@Composable
fun ScrollToTop(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .size(50.dp)
                .align(Alignment.BottomEnd),
            onClick = onClick,
            containerColor = White, contentColor = Black
        ) {
            Icon(
                painter = painterResource(id = hello.com.pose.ui.system.R.drawable.baseline_arrow_upward_24),
                contentDescription = "go to top"
            )
        }
    }
}

@Composable
fun LazyGridState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }

    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

private fun LazyGridScope.searchBar(
    onSearch: (String) -> Unit,
    onClickSetting: () -> Unit
) {
    item(span = { GridItemSpan(PhotoCellCount) }) {
        SearchBar(
            onSearch = onSearch,
            trailingIcon = { IconSetting(onClickSetting) }
        )
    }
}
private fun LazyGridScope.loadingItem() {
    item(
        span = { GridItemSpan(PhotoCellCount) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(102.dp),
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

internal const val PhotoCellCount = 3
