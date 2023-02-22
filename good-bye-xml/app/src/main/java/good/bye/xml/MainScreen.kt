package good.bye.xml

import android.util.Log
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter
import com.skydoves.orbital.Orbital
import com.skydoves.orbital.animateSharedElementTransition
import com.skydoves.orbital.rememberContentWithOrbitalScope
import good.bye.xml.download.PhotoDownloader
import good.bye.xml.ext.pxToDp
import good.bye.xml.ui.component.FlickrImageList
import good.bye.xml.ui.component.FlickrSearchBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val searchKeyword by viewModel.searchKeyword
    val searchedImages = viewModel.searchedImages.collectAsLazyPagingItems()

    var hasTextFieldFocus by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val lazyGridState = rememberLazyGridState()

    val downloadManager = PhotoDownloader(LocalContext.current)

    var downloadImage: AsyncImagePainter? by remember { mutableStateOf(null) }

    val coroutineScope = rememberCoroutineScope()

    var informationBtnOffset by remember { mutableStateOf(Offset.Zero) }
    var informationSize by remember { mutableStateOf(IntSize.Zero) }
    var isTransformed by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = isTransformed) {
        if (!isTransformed) {
            delay(300)
            downloadImage = null
        }
    }

    // 스크롤 중이면서, TextField가 포커스를 갖고 있다면 focus clear
    if (lazyGridState.isScrollInProgress && hasTextFieldFocus) {
        focusManager.clearFocus()
    }

    val onImageClick: (AsyncImagePainter, LayoutCoordinates) -> Unit =
        { imagePainter, layoutCoordinate ->
            downloadImage = imagePainter
            informationBtnOffset = layoutCoordinate.positionInRoot()
            informationSize = layoutCoordinate.size
            coroutineScope.launch {
                delay(100L)
                isTransformed = true
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        FlickrSearchBar(
            text = searchKeyword,
            onTextChange = viewModel::updateSearchKeyword,
            onTextFieldFocusChanged = { hasFocus -> hasTextFieldFocus = hasFocus },
            onSearchClick = {
                focusManager.clearFocus()
                viewModel.loadImages()
                coroutineScope.launch { scrollToTop(lazyGridState) }
            },
        )

        FlickrImageList(
            images = searchedImages,
            onClick = onImageClick,
            state = lazyGridState,
        )
    }
    if (downloadImage != null) {
        val poster = rememberContentWithOrbitalScope {
            Image(
                painter = downloadImage!!,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .then(
                        if (isTransformed) {
                            Modifier
                                .fillMaxWidth()
                                .height(620.dp)
                        } else {
                            Modifier
                                .size(
                                    informationSize.width.pxToDp(),
                                    informationSize.height.pxToDp(),
                                )
                                .offset(
                                    informationBtnOffset.x.pxToDp(),
                                    informationBtnOffset.y.pxToDp(),
                                )
                        },
                    )
                    .animateSharedElementTransition(
                        this@rememberContentWithOrbitalScope,
                        SpringSpec(stiffness = 500f),
                        SpringSpec(stiffness = 500f),
                    )
            )
        }

        Orbital(
            modifier = Modifier
                .fillMaxWidth()
                .height(620.dp)
                .clickable { isTransformed = !isTransformed }
        ) {
            if (isTransformed) {
                FlickDetail(
                    content = { poster() },
                    onConfirmClick = {
                        downloadImage?.run { request.data.toString() }?.run(downloadManager::downloadFile)
                        isTransformed = false
                                     },
                    onCancelClick = { isTransformed = false }
                )
            } else {
                poster()
            }
        }
    }
}

@Composable
fun FlickDetail(
    content: @Composable () -> Unit,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    Column {
        content()

        Row(Modifier.align(Alignment.CenterHorizontally)) {

            Button(onClick = onConfirmClick) { Text(text = "확인") }

            Button(onClick = onCancelClick) { Text(text = "취소") }

        }
    }
}

suspend fun scrollToTop(grid: LazyGridState) {
    grid.animateScrollToItem(0)
}
