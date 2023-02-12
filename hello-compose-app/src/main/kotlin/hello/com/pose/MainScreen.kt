package hello.com.pose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import hello.com.pose.composable.Alert
import hello.com.pose.composable.SearchBar
import hello.com.pose.shared.domain.photo.Photo
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val currentSearchQuery by viewModel.searchQuery.collectAsState()

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
            SearchBar(text = currentSearchQuery,
                inputChange = {
                    viewModel.setNewQuery(it)
                })
            SearchList(viewModel, lazyGridState)
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
        state = gridState
    ) {
        items(viewModel.photoList) { photo ->
            MainContentItem(photo = photo)
        }
    }
}

val LazyGridState.isLastItemVisible: Boolean
    get() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContentItem(photo: Photo) {
    val showDownloadDialog = remember { mutableStateOf(false) }

    if (showDownloadDialog.value) {
        Alert(
            showDialog = showDownloadDialog.value,
            onDismiss = { showDownloadDialog.value = false },
            photo
        )
    }

    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clickable(onClick = {
                showDownloadDialog.value = true
            })
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = photo.getStaticImageUrl(),
                imageLoader = ImageLoader.Builder(context = LocalContext.current)
                    .crossfade(true)
                    .build()
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}

