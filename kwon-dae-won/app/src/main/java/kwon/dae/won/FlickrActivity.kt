package kwon.dae.won

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import kwon.dae.won.ui.DefaultAlertDialog
import kwon.dae.won.ui.FlickrApp
import kwon.dae.won.ui.imageUrl

@AndroidEntryPoint
class FlickrActivity : AppCompatActivity() {

    private val viewModel: FlickrViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val lazyPagingPhotos = viewModel.getRecentPhotos().collectAsLazyPagingItems()
            val photos by remember { mutableStateOf(lazyPagingPhotos) }
            var openDialog by remember { mutableStateOf(false to -1) }

            FlickrApp(
                photos = photos,
                onLongClick = { index ->
                    openDialog = true to index
                }
            )

            if (openDialog.first) {
                DefaultAlertDialog(
                    onDismissButtonClick = { openDialog = false to -1 },
                    onConfirmButtonClick = {
                        if (openDialog.second > 0) {
                            photos[openDialog.second]?.let { item ->
                                viewModel.downloadPhoto(
                                    item.title,
                                    item.owner,
                                    imageUrl(item.id, item.secret)
                                )
                            }
                        }
                        openDialog = false to -1
                    }
                )
            }
        }
    }
}
