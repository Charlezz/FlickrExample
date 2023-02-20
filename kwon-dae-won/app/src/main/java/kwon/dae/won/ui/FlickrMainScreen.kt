package kwon.dae.won.ui

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
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.flowOf
import kwon.dae.won.R
import kwon.dae.won.domain.model.Photo
import kwon.dae.won.domain.model.fake

/**
 * @author Daewon on 13,February,2023
 *
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrentPhotoList(
    photos: LazyPagingItems<Photo>,
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
                                        onClick = { },
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
                    } ?: PlaceHodlerItem()
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
fun PlaceHodlerItem() {
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
    CurrentPhotoList(
        photos = flowOf(PagingData.from(Photo.fake())).collectAsLazyPagingItems(),
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

