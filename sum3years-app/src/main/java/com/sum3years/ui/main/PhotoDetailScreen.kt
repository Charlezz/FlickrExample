package com.sum3years.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sum3years.model.PhotoUIModel
import com.sum3years.ui.theme.FlickrExampleTheme

@Composable
fun PhotoDetail(
    photo: PhotoUIModel,
    modifier: Modifier = Modifier,
    onDownloadClick: () -> Unit,
    onClose: () -> Unit,
) {
    Log.d("로그", "_PhotoDetail: ratio: ${photo.ratio}")
    Column(
        modifier = modifier
            .padding(bottom = 30.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.aspectRatio(photo.ratio),
        ) {
            AsyncImage(
                model = photo.loadUrlMedium,
                contentDescription = "Photo detail : ${photo.title}",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(),
            )
            IconButton(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd),
                onClick = { onClose() },
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close photo detail screen",
                    modifier = Modifier
                        .drawBehind {
                            drawCircle(
                                color = Color.White.copy(alpha = .5f),
                                radius = size.maxDimension,
                            )
                        },
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = photo.title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { onDownloadClick() }) {
            Text(text = "다운로드")
        }
    }
}

@Preview
@Composable
fun PhotoDetailPreview() {
    FlickrExampleTheme {
        PhotoDetail(photo = PhotoUIModel(), onDownloadClick = {}) {}
    }
}
