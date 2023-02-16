package good.bye.xml.ui.component

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import good.bye.xml.domain.model.photo.Photo

@Composable
fun FlickrImageList(
    images: LazyPagingItems<Photo>,
    modifier: Modifier = Modifier,
    onClick: ((String) -> Unit) = {},
    state: LazyGridState = rememberLazyGridState()
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        state = state
    ) {
        items(images.itemCount) { index ->
            val image = images[index]!!.formattedUrl
            FlickrImage(
                imagePath = images[index]!!.formattedUrl,
                onClick = { onClick(image) }
            )
        }
    }
}
