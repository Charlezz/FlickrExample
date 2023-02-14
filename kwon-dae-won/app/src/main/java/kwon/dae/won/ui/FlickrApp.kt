package kwon.dae.won.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import kwon.dae.won.FlickrViewModel


/**
 * @author Daewon on 10,February,2023
 *
 */
@Composable
fun FlickrApp(
    viewModel: FlickrViewModel = viewModel(),
) {
    val lazyPagingPhotos = viewModel.getRecentPhotos().collectAsLazyPagingItems()
    val photos by remember { mutableStateOf(lazyPagingPhotos) }
    var openDialog by remember { mutableStateOf(false to "") }

    Column(modifier = Modifier.fillMaxSize()) {

        CurrentPhotoList(photos = photos, onLongClick = { url ->
            openDialog = true to url
        })
    }

    if (openDialog.first) {
        openDialog.second.let { url ->
            DefaultAlertDialog(
                url = url,
                onDismissButtonClick = { openDialog = false to "" },
                onConfirmButtonClick = {
                    // TODO 파일 이름 정하기
                    viewModel.downloadPhoto(
                        "",
                        "",
                        url
                    )
                    openDialog = false to ""
                }
            )
        }
    }
}
