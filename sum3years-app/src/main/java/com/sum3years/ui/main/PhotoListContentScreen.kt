package com.sum3years.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
            columns = GridCells.Adaptive(minSize = 150.dp),
            state = scrollState,
            contentPadding = WindowInsets.systemBars
                .only(WindowInsetsSides.Bottom)
                .add(
                    WindowInsets(left = 8.dp, right = 8.dp, top = 16.dp, bottom = 16.dp),
                ).asPaddingValues(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(
                items = photoList,
                key = { _: Int, item: PhotoUIModel ->
                    item.id
                },
            ) { index: Int, photo: PhotoUIModel ->
                loadedItems = maxOf(loadedItems, index + 1)
                Box(modifier = Modifier.defaultMinSize(150.dp, 150.dp).fillMaxSize()) {
                    AsyncImage(
                        model = photo.loadUrlSmall,
                        contentDescription = null,
                        modifier = Modifier.clickable { onClick(photo) },
                    )
                }
            }
        }
    }
}

@Composable
fun PhotoListContentMock(
    modifier: Modifier = Modifier,
    photoList: List<PhotoUIModel>,
    onClick: (PhotoUIModel) -> Unit,
    endOfList: (Boolean) -> Unit,
) {
    val itemCount = photoList.size
    val scrollState = rememberLazyGridState(itemCount)
    var loadedItems = 0

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
            columns = GridCells.Adaptive(minSize = 300.dp),
            state = scrollState,
            contentPadding = WindowInsets.systemBars
                .only(WindowInsetsSides.Bottom)
                .add(
                    WindowInsets(left = 8.dp, right = 8.dp, top = 16.dp, bottom = 16.dp),
                ).asPaddingValues(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(
                items = photoList,
                key = { _: Int, item: PhotoUIModel ->
                    item.id
                },
            ) { index: Int, photo: PhotoUIModel ->
                loadedItems = maxOf(loadedItems, index + 1)
                Box(
                    modifier = Modifier.clickable {
                        onClick(photo)
                    },
                ) {
                    Image(imageVector = Icons.Default.Person, contentDescription = null)
                }
            }
        }
    }
}

@Preview
@Composable
fun PhotoListContentPreview() {
    val photos = listOf(
        PhotoUIModel(
            id = "52417436827",
            owner = "72988954@N00",
            secret = "52b528c896",
            server = "65535",
            title = "Park City Mammoth Cave KY 1940s Spelunking CRYSTAL LAKE BOAT TOUR @ ONYX Mammoth Cave National Park A Major Flint Ridge Cave Edmund Turner & LP Edwards History1",
        ),
        PhotoUIModel(
            id = "52644426954",
            owner = "72988954@N00",
            secret = "3a9fe9432c",
            server = "65535",
            title = "SHIP Houghton Hancock MI c.1915 Unusual Barrels of Copper & Ingots to load @ Steamer Freighter Docks & Copper Range RR Yards RED JACKET CALUMET HECLA DOLLAR BAY Smelted Ingots Portage Lake KEWEENAW HISTORY1",
        ),
        PhotoUIModel(
            id = "52417436827",
            owner = "72988954@N00",
            secret = "52b528c896",
            server = "65535",
            title = "Park City Mammoth Cave KY 1940s Spelunking CRYSTAL LAKE BOAT TOUR @ ONYX Mammoth Cave National Park A Major Flint Ridge Cave Edmund Turner & LP Edwards History1",
        ),
        PhotoUIModel(
            id = "52644426954",
            owner = "72988954@N00",
            secret = "3a9fe9432c",
            server = "65535",
            title = "SHIP Houghton Hancock MI c.1915 Unusual Barrels of Copper & Ingots to load @ Steamer Freighter Docks & Copper Range RR Yards RED JACKET CALUMET HECLA DOLLAR BAY Smelted Ingots Portage Lake KEWEENAW HISTORY1",
        ),
        PhotoUIModel(
            id = "52417436827",
            owner = "72988954@N00",
            secret = "52b528c896",
            server = "65535",
            title = "Park City Mammoth Cave KY 1940s Spelunking CRYSTAL LAKE BOAT TOUR @ ONYX Mammoth Cave National Park A Major Flint Ridge Cave Edmund Turner & LP Edwards History1",
        ),
        PhotoUIModel(
            id = "52644426954",
            owner = "72988954@N00",
            secret = "3a9fe9432c",
            server = "65535",
            title = "SHIP Houghton Hancock MI c.1915 Unusual Barrels of Copper & Ingots to load @ Steamer Freighter Docks & Copper Range RR Yards RED JACKET CALUMET HECLA DOLLAR BAY Smelted Ingots Portage Lake KEWEENAW HISTORY1",
        ),
    )
    PhotoListContentMock(photoList = photos, onClick = {}, endOfList = {})
}
