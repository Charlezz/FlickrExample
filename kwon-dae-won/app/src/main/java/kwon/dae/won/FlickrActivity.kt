package kwon.dae.won

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import kwon.dae.won.ui.FlickrApp

@AndroidEntryPoint
class FlickrActivity : AppCompatActivity() {

    private val viewModel: FlickrViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val photos = viewModel.getRecentPhotos().collectAsLazyPagingItems()
            FlickrApp(photos)
        }
    }
}
