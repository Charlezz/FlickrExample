package good.bye.xml.ui.component

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImagePainter
import good.bye.xml.domain.model.photo.Photo

@Composable
fun FlickrImageList(
    images: LazyPagingItems<Photo>,
    modifier: Modifier = Modifier,
    onClick: ((AsyncImagePainter, LayoutCoordinates) -> Unit) = { _, _ -> },
    state: LazyGridState = rememberLazyGridState()
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        state = state

    ) {
        items(images.itemCount) { index ->
            images.get(index)?.run {
                FlickrImage(
                    imagePath = formattedUrl,
                    onClick = { layoutCoordinates, asyncImagePainter ->
                        onClick(asyncImagePainter, layoutCoordinates)
                    }
                )
            }
        }
    }
}
