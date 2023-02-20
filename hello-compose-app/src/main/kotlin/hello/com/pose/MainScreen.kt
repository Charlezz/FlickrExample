package hello.com.pose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import hello.com.pose.composable.Alert
import hello.com.pose.composable.SearchBar
import hello.com.pose.presentation.main.PhotoList
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
                },
            )
            PhotoList(pagingItems, viewModel::onClickPhoto)
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
fun SearchList(viewModel: MainViewModel, gridState: LazyGridState) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        state = gridState,
    ) {
        items(viewModel.photoList) { photo ->
            MainContentItem(photo = photo)
        }
    }
}

val LazyGridState.isLastItemVisible: Boolean
    get() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

@Composable
fun MainContentItem(photo: Photo) {
    val showDownloadDialog = remember { mutableStateOf(false) }

    if (showDownloadDialog.value) {
        Alert(
            showDialog = showDownloadDialog.value,
            onDismiss = { showDownloadDialog.value = false },
            photo,
        )
    }
}

