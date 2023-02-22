package kwon.dae.won.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.flowOf
import kwon.dae.won.R
import kwon.dae.won.component.FlickrTopAppBar
import kwon.dae.won.domain.model.Photo
import kwon.dae.won.domain.model.fake
import kwon.dae.won.ui.SearchBar
import kwon.dae.won.ui.imageUrl
import kwon.dae.won.ui.loadImageData

/**
 * @author Daewon on 13,February,2023
 *
 */

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit = {},
    onPhotoShortClick: (String) -> Unit,
) {
    val lazyPagingPhotos = viewModel.recentImage.collectAsLazyPagingItems()
    val photos by remember { mutableStateOf(lazyPagingPhotos) }
    var openDialog by remember { mutableStateOf(false to -1) }
    val snackbarHostState = remember { SnackbarHostState() }
    val keyWord by viewModel.searchKeyword.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            FlickrTopAppBar(
                onNavigationIconClick = onNavigationIconClick,
                title = {
                    Image(
                        modifier = Modifier.size(30.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.charlezz),
                        contentDescription = null
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Surface(
            modifier = Modifier
                .padding(top = padding.calculateTopPadding(), start = 0.dp, end = 0.dp, bottom = 0.dp)
        ) {
            PhotoPagingList(
                modifier = Modifier,
                paddingValues = padding,
                photos = photos,
                onShortClick = { index ->
                    photos[index]?.let {
                        onPhotoShortClick(imageUrl(it.id, it.secret))
                    }
                },
                onLongClick = { index ->
                    openDialog = true to index
                },
                keyWord = keyWord,
                onValueChange = {
                    viewModel.setKeyword(it)
                },
                onSearchClick = {
                    viewModel.searchKeyWordPhotos(keyWord)
                },
                onCancelClick = {
                    viewModel.resetKeyword()
                }
            )
        }

        if (openDialog.first) {
            photos[openDialog.second]?.let { photo ->
                DefaultAlertDialog(
                    url = imageUrl(photo.id, photo.secret),
                    onDismissButtonClick = { openDialog = false to -1 },
                    onConfirmButtonClick = {
                        viewModel.downloadPhoto(
                            photo.title.ifBlank { photo.id },
                            photo.owner,
                            imageUrl(photo.id, photo.secret)
                        )
                        openDialog = false to -1
                    }
                )
            }
        }
    }


    LaunchedEffect(Unit) {
        viewModel.suggestQuery.collect {
            viewModel.searchKeyWordPhotos(it)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoPagingList(
    modifier: Modifier,
    paddingValues: PaddingValues,
    photos: LazyPagingItems<Photo>,
    onShortClick: (Int) -> Unit,
    onLongClick: (Int) -> Unit,
    keyWord: String,
    onValueChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    val scrollState = rememberLazyGridState()
    val scrollingUp = scrollState.isScrollingUp()

    Column {
        AnimatedVisibility(scrollingUp) {
            SearchBar(
                text = keyWord,
                onValueChange = onValueChange,
                onSearchClick = onSearchClick,
                onCancelClick = onCancelClick
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            state = scrollState,
        ) {
            items(
                count = photos.itemCount,
            ) { index ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    photos[index]?.let { photo ->
                        var isImageLoading by remember { mutableStateOf(false) }

                        val painter = rememberAsyncImagePainter(
                            model = loadImageData(
                                LocalContext.current,
                                photo.id,
                                photo.secret
                            )
                        )

                        isImageLoading = when (painter.state) {
                            is AsyncImagePainter.State.Loading -> true
                            else -> false
                        }

                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(150.dp)
                                    .combinedClickable(
                                        onClick = {
                                            onShortClick(index)
                                        },
                                        onLongClick = {
                                            onLongClick(index)
                                        }
                                    ),
                                painter = painter,
                                contentDescription = "Photo Image",
                                contentScale = ContentScale.Crop,
                            )

                            if (isImageLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(horizontal = 6.dp, vertical = 3.dp),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    } ?: PlaceHolderItem()
                }
            }
        }
    }

    LaunchedEffect(keyWord) {
        scrollState.animateScrollToItem(0)
    }

}

@Composable
fun DefaultAlertDialog(
    url: String,
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismissButtonClick() },
        title = {
            Text(text = "다운로드 하시겠습니까?")
        },
        text = {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    modifier = Modifier.align(Alignment.Center),
                    model = url,
                    contentDescription = null
                )
            }

        },
        dismissButton = {
            TextButton(
                onClick = { onDismissButtonClick() }
            ) {
                Text("취소")
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirmButtonClick() }
            ) {
                Text("확인")
            }
        },
    )
}

@Composable
fun PlaceHolderItem() {
    Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.charlezz),
        contentDescription = null
    )
}

@Composable
private fun LazyGridState.isScrollingUp(): Boolean {
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

@Preview(name = "PhotoList", showSystemUi = true)
@Composable
fun PreviewCurrentPhotoList() {
    PhotoPagingList(
        modifier = Modifier,
        paddingValues = PaddingValues(0.dp),
        photos = flowOf(PagingData.from(Photo.fake())).collectAsLazyPagingItems(),
        onShortClick = { },
        onLongClick = { },
        keyWord = "Charlezz",
        onValueChange = { },
        onSearchClick = { },
        onCancelClick = { }
    )
}

@Preview(name = "DownLoadDialog", widthDp = 300, heightDp = 300)
@Composable
fun PreviewDefaultDialog() {
    DefaultAlertDialog(
        url = "",
        onConfirmButtonClick = { },
        onDismissButtonClick = { }
    )
}

