package kwon.dae.won.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kwon.dae.won.domain.model.Photo
import timber.log.Timber

/**
 * @author Daewon on 13,February,2023
 *
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrentPhotoList(
    photos: LazyPagingItems<Photo>,
    onLongClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
    ) {
        items(photos.itemCount) { index ->
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (photos[index]?.id?.isNotBlank() == true) {
                    var isImageLoading by remember { mutableStateOf(false) }

                    val painter = rememberAsyncImagePainter(
                        model = loadImageData(
                            LocalContext.current,
                            photos[index]?.id,
                            photos[index]?.secret
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
                                        Timber
                                            .tag("사진")
                                            .d(photos[index]?.id)
                                    },
                                    onLongClick = { onLongClick(index) }
                                ),
                            painter = painter,
                            contentDescription = "Photo Image",
                            contentScale = ContentScale.FillBounds,
                        )

                        if (isImageLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(horizontal = 6.dp, vertical = 3.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                }
            }
        }
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
                AsyncImage(modifier = Modifier.align(Alignment.Center),model = url, contentDescription = null)
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
