package hello.com.pose.presentation.main

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import hello.com.pose.shared.domain.photo.Photo
import hello.com.pose.ui.system.SearchBar

@Composable
internal fun MainRoute(
    onNavigateDetail: (Photo) -> Unit,
    onNavigateSetting: () -> Unit,
    mainViewModel: MainScreenViewModel = hiltViewModel()
) {
    val pagingItems = mainViewModel.getPagingFlow("bird").collectAsLazyPagingItems()
    MainScreen(onNavigateDetail, onNavigateSetting, mainViewModel::onDoneSearch, pagingItems)
}

@Composable
internal fun MainScreen(
    onNavigateDetail: (Photo) -> Unit,
    onNavigateSetting: () -> Unit,
    onDoneSearch: (String) -> Unit,
    pagingItems: LazyPagingItems<Photo>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PhotoList(
            pagingItems = pagingItems,
            onClickPhoto = onNavigateDetail,
            onDoneSearch = onDoneSearch
        )
    }
}

@Composable
fun PhotoList(
    pagingItems: LazyPagingItems<Photo>,
    onClickPhoto: (Photo) -> Unit,
    onDoneSearch: (String) -> Unit,
) {
    val scrollState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(PhotoCellCount),
        modifier = Modifier
            .fillMaxSize(),
        state = scrollState,
    ) {
        searchBar(onDoneSearch)
        items(
            count = pagingItems.itemCount
        ) { index ->
            pagingItems[index]?.let { photo ->
                Photo(photo, onClickPhoto)
            }
        }

        when {
            pagingItems.loadState.refresh is LoadState.Loading -> loadingItem()
            pagingItems.loadState.append is LoadState.Loading -> loadingItem()
        }
    }
}

private fun LazyPagingItems<Photo>.key(index: Int): Any {
    return get(index)?.id ?: index
}

private fun LazyGridScope.searchBar(onDoneSearch: (String) -> Unit) {
    item(span = { GridItemSpan(PhotoCellCount) }) {
        SearchBar(onDoneSearch)
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
