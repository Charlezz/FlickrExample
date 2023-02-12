package com.sum3years.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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
    val scrollState = rememberLazyGridState(itemCount)
    var loadedItems = 0

    val minSize: Dp = 100.dp

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xffEEEEEE),
    ) {
        // Reached the end
        if (loadedItems == itemCount && !scrollState.isScrollInProgress) {
            endOfList(true)
        }
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
                key = { _: Int, item: PhotoUIModel ->
                    item.id
                },
            ) { index: Int, photo: PhotoUIModel ->
                loadedItems = maxOf(loadedItems, index + 1)
                Box(modifier = Modifier.width(minSize).aspectRatio(1f).fillMaxSize()) {
                    AsyncImage(
                        model = photo.loadUrlSmall,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().clickable { onClick(photo) },
                    )
                }
            }
        }
    }
}