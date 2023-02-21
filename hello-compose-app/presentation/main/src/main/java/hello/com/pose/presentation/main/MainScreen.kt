package hello.com.pose.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import hello.com.pose.shared.domain.photo.Photo
import hello.com.pose.ui.system.SearchBar
import androidx.paging.compose.collectAsLazyPagingItems as collectAsLazyPagingItems1

@Composable
internal fun MainRoute(
    onNavigateDetail: (Photo) -> Unit,
    onNavigateSetting: () -> Unit,
    mainViewModel: MainScreenViewModel = hiltViewModel()
) {
    val state = mainViewModel.stateFlow.collectAsState().value

    MainScreen(
        onNavigateDetail = onNavigateDetail,
        onNavigateSetting = onNavigateSetting,
        onSearch = { mainViewModel.eventHandler(MainContract.Event.DoSearchPhoto(it)) },
        state = state
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun MainScreen(
    onNavigateDetail: (Photo) -> Unit,
    onNavigateSetting: () -> Unit,
    onSearch: (String) -> Unit,
    state: MainContract.State
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PhotoList(
            state = state.photoListState,
            onClickPhoto = onNavigateDetail,
            onSearch = {
                onSearch(it)
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun PhotoList(
    state: MainContract.State.PhotoListState,
    onClickPhoto: (Photo) -> Unit,
    onSearch: (String) -> Unit,
) {
    val scrollState = rememberLazyGridState()

    val pagingItems = if (state is MainContract.State.PhotoListState.Success)
        state.pagingDataFlow.collectAsLazyPagingItems1()
    else null

    LazyVerticalGrid(
        columns = GridCells.Fixed(PhotoCellCount),
        modifier = Modifier
            .fillMaxSize(),
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        searchBar(onSearch)
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

private fun LazyPagingItems<Photo>.key(index: Int): Any {
    return get(index)?.id ?: index
}

private fun LazyGridScope.searchBar(onSearch: (String) -> Unit) {
    item(span = { GridItemSpan(PhotoCellCount) }) {
        SearchBar(onSearch)
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
