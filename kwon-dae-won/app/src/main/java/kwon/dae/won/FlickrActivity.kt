package kwon.dae.won

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlickrActivity : AppCompatActivity() {

    private val viewModel: FlickrViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO: 대원님 화이팅!
            val photos = viewModel.getRecentPhotos().collectAsLazyPagingItems()
            FlickrScreen(photos)
        }
    }
}
