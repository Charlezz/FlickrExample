package good.bye.xml.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import good.bye.xml.R
import good.bye.xml.ui.theme.GoodByeXmlTheme

@Composable
fun FlickrImage(
    imagePath: String,
    modifier: Modifier = Modifier,
    onClick: ((LayoutCoordinates, AsyncImagePainter) -> Unit) = { layoutCoordinates: LayoutCoordinates, asyncImagePainter: AsyncImagePainter -> },
) {
    lateinit var layoutCoordinate: LayoutCoordinates

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imagePath)
            .placeholder(R.drawable.bg_coil_placeholder)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop
    )

    Image(
        painter = painter,
        contentDescription = "coil image load",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(100.dp)
            .clickable {
                if (painter.state is AsyncImagePainter.State.Success) {
                    onClick(layoutCoordinate, painter)
                }
            }
            .onGloballyPositioned {
                layoutCoordinate = it
            }
    )
}

@Preview("FlickrImage Component")
@Composable
private fun FlickrImagePreview() {
    GoodByeXmlTheme {
        Surface {
            FlickrImage(imagePath = "https://images.pexels.com/photos/2662116/pexels-photo-2662116.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2")
        }
    }
}
