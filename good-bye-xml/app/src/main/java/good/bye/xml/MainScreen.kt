package good.bye.xml

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import good.bye.xml.ui.component.FlickrImageList
import good.bye.xml.ui.component.FlickrSearchBar


@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {

    val searchKeyword by viewModel.searchKeyword
    val searchedImages = viewModel.searchedImages.collectAsLazyPagingItems()

    val focusManager = LocalFocusManager.current

    val onSearchClick = {
        focusManager.clearFocus()
        viewModel.search(searchKeyword)
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
            onTextChange = { text -> viewModel.updateSearchKeyword(text) },
            onSearchClick = onSearchClick
        )

        FlickrImageList(
            images = searchedImages,
            onClick = onImageClick,
            modifier = Modifier
        )
    }
}
