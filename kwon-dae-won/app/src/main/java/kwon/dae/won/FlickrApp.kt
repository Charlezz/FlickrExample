package kwon.dae.won

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import kwon.dae.won.domain.model.Photo


/**
 * @author Daewon on 10,February,2023
 *
 */
@Composable
fun FlickrApp(photos: LazyPagingItems<Photo>) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        items(photos.itemCount) { index ->
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TODO : 상수로 빼기
                val serverId = 65535
                val url = "https://live.staticflickr.com/${serverId}"
                if (photos[index]?.id?.isNotBlank() == true) {
                    var isImageLoading by remember { mutableStateOf(false) }

                    val painter = rememberAsyncImagePainter(
                        // TODO : 함수 만들기
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(url + "/${photos[index]?.id}_${photos[index]?.secret}.jpg")
                            .crossfade(true)
                            .size(Size.ORIGINAL)
                            .build()
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
                                .height(150.dp)
                                .width(150.dp),
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
