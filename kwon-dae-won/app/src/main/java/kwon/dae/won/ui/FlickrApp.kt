package kwon.dae.won.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import kwon.dae.won.FlickrViewModel


/**
 * @author Daewon on 10,February,2023
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickrApp(
    viewModel: FlickrViewModel = viewModel(),
) {
    val lazyPagingPhotos = viewModel.recentImage.collectAsLazyPagingItems()
    val photos by remember { mutableStateOf(lazyPagingPhotos) }
    var openDialog by remember { mutableStateOf(false to -1) }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {

            CurrentPhotoList(photos = photos, onLongClick = { index ->
                openDialog = true to index
            })
        }

    }

    if (openDialog.first) {
        photos[openDialog.second]?.let { photo ->
            DefaultAlertDialog(
                url = imageUrl(photo.id, photo.secret),
                onDismissButtonClick = { openDialog = false to -1 },
                onConfirmButtonClick = {
                    // TODO 파일 이름 정하기
                    viewModel.downloadPhoto(
                        photo.title.ifBlank { photo.id },
                        photo.owner,
                        imageUrl(photo.id, photo.secret)
                    )
                    openDialog = false to -1
                }
            )
        }
    }
}
