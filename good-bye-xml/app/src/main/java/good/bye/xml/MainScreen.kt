package good.bye.xml

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import good.bye.xml.ui.component.FlickrImageList
import good.bye.xml.ui.component.FlickrSearchBar


@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {

    val searchKeyword by viewModel.searchKeyword
    val searchedImages = viewModel.searchedImages.collectAsLazyPagingItems()

    var hasTextFieldFocus by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val lazyGridState = rememberLazyGridState()

    // 스크롤 중이면서, TextField가 포커스를 갖고 있다면 focus clear
    if (lazyGridState.isScrollInProgress && hasTextFieldFocus) {
        focusManager.clearFocus()
    }

    val onImageClick = {
        /* TODO : 다운로드 팝업 표출 */
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
                viewModel.search(searchKeyword)
            }
        )

        FlickrImageList(
            images = searchedImages,
            onClick = onImageClick,
            state = lazyGridState
        )
    }
}