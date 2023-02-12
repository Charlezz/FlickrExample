package good.bye.xml.ui.component

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import good.bye.xml.domain.model.photo.Photo

@Composable
fun FlickrImageList(
    images: LazyPagingItems<Photo>,
    onClick: (() -> Unit) = {},
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier
    ) {
        items(images.itemCount) { index ->
            FlickrImage(
                imagePath = images[index]!!.formattedUrl,
                onClick = onClick
            )

        }
    }
}
