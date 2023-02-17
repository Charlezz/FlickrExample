package com.sum3years.ui.main

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sum3years.R
import com.sum3years.model.PhotoUIModel

@ExperimentalAnimationApi
@Composable
fun PhotoListContent(
    modifier: Modifier = Modifier,
    photoList: List<PhotoUIModel>,
    onClick: (PhotoUIModel) -> Unit,
    endOfList: (Boolean) -> Unit,
) {
    val itemCount = photoList.size
    val scrollState = rememberLazyGridState()
    var loadedItems = 0

    val minSize: Dp = 100.dp

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xffEEEEEE),
    ) {
        // List of Photos
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(minSize = minSize),
            state = scrollState,
            contentPadding = WindowInsets.systemBars
                .only(WindowInsetsSides.Bottom)
                .add(
                    WindowInsets(left = 8.dp, right = 8.dp, top = 16.dp, bottom = 16.dp),
                ).asPaddingValues(),
        ) {
            itemsIndexed(
                items = photoList,
                key = { index: Int, item: PhotoUIModel ->
                    "$index/${item.photoId}"
                },
            ) { index: Int, photo: PhotoUIModel ->
                loadedItems = maxOf(loadedItems, index + 1)

                val painter = rememberAsyncImagePainter(
                    model = photo.loadUrlSmall,
                    placeholder = painterResource(id = R.drawable.charlezzicon),
                )
                val imageRatio = remember(painter.state) {
                    val imageSize = painter.intrinsicSize
                    imageSize.width / imageSize.height
                }
                Box(
                    modifier = Modifier
                        .width(minSize)
                        .aspectRatio(1f)
                        .fillMaxSize(),
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { onClick(photo.apply { ratio = imageRatio }) },
                    )
                }
            }
        }
        // Reached the end
        LaunchedEffect(scrollState.isScrollInProgress) {
            if (loadedItems == itemCount && !scrollState.isScrollInProgress) {
                Log.d("로그", "_PhotoListContent: Bottom reached!!")
                endOfList(true)
            }
        }
    }
}
