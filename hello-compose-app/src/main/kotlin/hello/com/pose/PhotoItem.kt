package hello.com.pose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import hello.com.pose.composable.Alert
import hello.com.pose.shared.domain.photo.Photo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoScreen(photo: Photo) {
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
            .fillMaxWidth()
            .aspectRatio(1f)
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