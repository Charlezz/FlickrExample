package good.bye.xml

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.skydoves.orbital.Orbital
import com.skydoves.orbital.animateTransformation
import com.skydoves.orbital.rememberContentWithOrbitalScope
import good.bye.xml.download.PhotoDownloader
import good.bye.xml.ext.pxToDp
import good.bye.xml.ui.component.FlickrImageList
import good.bye.xml.ui.component.FlickrSearchBar
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val searchKeyword by viewModel.searchKeyword
    val searchedImages = viewModel.searchedImages.collectAsLazyPagingItems()

    var hasTextFieldFocus by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val lazyGridState = rememberLazyGridState()

    val downloadManager = PhotoDownloader(LocalContext.current)

    var isDialogShow by remember { mutableStateOf(false) }

    var downloadImage = ""

    val coroutineScope = rememberCoroutineScope()

    var informationBtnOffset by remember { mutableStateOf(Offset.Zero) }
    var informationSize by remember { mutableStateOf(IntSize.Zero) }

    Log.d("Test", "MainScreen: width ${informationSize.width.pxToDp()}")
    Log.d("Test", "MainScreen: height${informationSize.height.pxToDp()}")
    Log.d("Test", "MainScreen: x ${informationBtnOffset.x.pxToDp()}")
    Log.d("Test", "MainScreen: y ${informationBtnOffset.y.pxToDp()}")


    LaunchedEffect(Unit) {
        lazyGridState.animateScrollToItem(0)
    }

    //

    // 스크롤 중이면서, TextField가 포커스를 갖고 있다면 focus clear
    if (lazyGridState.isScrollInProgress && hasTextFieldFocus) {
        focusManager.clearFocus()
    }

    val onImageClick: (String) -> Unit = { image ->
        downloadImage = image
        isDialogShow = true
    }

    Surface(Modifier.fillMaxSize()) {
        if (isDialogShow) {
            Dialog(onDismissRequest = { isDialogShow = false }) {
                Column(modifier = Modifier.background(Color.White)) {
                    Text(text = "사진을 다운로드 하시겠습니까?")
                    Spacer(modifier = Modifier.height(30.dp))
                    Row(Modifier.align(Alignment.CenterHorizontally)) {
                        Button(onClick = { downloadManager.downloadFile(downloadImage) }) {
                            Text(text = "확인")
                        }
                        Button(onClick = { isDialogShow = false }) { Text(text = "취소") }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FlickrSearchBar(
            text = searchKeyword,
            onTextChange = viewModel::updateSearchKeyword,
            onTextFieldFocusChanged = { hasFocus -> hasTextFieldFocus = hasFocus },
            onSearchClick = {
                focusManager.clearFocus()
                viewModel.loadImages()
                coroutineScope.launch { scrollToTop(lazyGridState) }
            }
        )

        FlickrImageList(
            images = searchedImages,
            onClick = onImageClick,
            state = lazyGridState
        )
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
//        Surface(
//            modifier = Modifier.width(informationSize.width.pxToDp())
//                .height(informationSize.height.pxToDp())
//                .offset(informationBtnOffset.x.pxToDp(), informationBtnOffset.y.pxToDp()),
//            color = Color.White
//        ) {
//
//        }

        val transformationSpec = SpringSpec<IntSize>(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 200f
        )

        var isTransformed by rememberSaveable { mutableStateOf(false) }
//        val poster = rememberContentWithOrbitalScope {
//            AsyncImage(
//                modifier = if (isTransformed) {
//                    Modifier.size(300.dp, 620.dp)
//                } else {
//                    Modifier.size(100.dp, 220.dp)
//                }.animateTransformation(this, transformationSpec)
//            )
//        }

        Orbital(
            modifier = Modifier.clickable { isTransformed = !isTransformed }
        ) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
//                poster()
            }
        }

    }
}

suspend fun scrollToTop(grid: LazyGridState) {
    grid.animateScrollToItem(0)
}
