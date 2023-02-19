package hello.com.pose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import hello.com.pose.composable.SearchBar
import hello.com.pose.shared.domain.photo.Photo
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val currentSearchQuery by viewModel.searchQuery.collectAsState()

    val pagingItems = viewModel.getPagingFlow("bird").collectAsLazyPagingItems()

    val shouldStartPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate && lazyGridState.isLastItemVisible
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && viewModel.pagingState == PagingState.IDLE) {
            viewModel.getPhotosByQuery(currentSearchQuery)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(8.dp)) {
            SearchBar(
                text = currentSearchQuery,
                inputChange = {
                    viewModel.setNewQuery(it)
                }
            )
            SearchList(viewModel.photoList, lazyGridState)
            PhotoList(pagingItems)
            LaunchedEffect(key1 = currentSearchQuery) {
                if (viewModel.prevQuery != currentSearchQuery) {
                    coroutineScope.launch {
                        lazyGridState.animateScrollToItem(index = 0)
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoList(pagingItems: LazyPagingItems<Photo>) {
    val scrollState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        state = scrollState,
    ) {
        items(pagingItems.itemCount) { index ->
            pagingItems[index]?.let { photo ->
                PhotoScreen(photo = photo)
            }
        }

        when {
            pagingItems.loadState.refresh is LoadState.Loading -> loadingItem()
            pagingItems.loadState.append is LoadState.Loading -> loadingItem()
        }
    }
}

private fun LazyGridScope.loadingItem() {
    item(
        span = { GridItemSpan(2) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(102.dp),
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Composable
fun SearchList(
    photos: MutableList<Photo>,
    gridState: LazyGridState
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        state = gridState,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(photos) { photo ->
            PhotoScreen(photo)
        }
    }
}

val LazyGridState.isLastItemVisible: Boolean
    get() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1