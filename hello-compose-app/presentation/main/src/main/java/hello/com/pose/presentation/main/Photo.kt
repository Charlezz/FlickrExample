package hello.com.pose.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import hello.com.pose.shared.domain.photo.Photo
import hello.com.pose.ui.image.AsyncImage

@Composable
fun Photo(
    photo: Photo,
    onClickPhoto: (Photo) -> Unit,
) {
    AsyncImage(
        model = photo.getStaticImageUrl(),
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClickPhoto(photo) },
        contentScale = ContentScale.Crop,
    )
}
