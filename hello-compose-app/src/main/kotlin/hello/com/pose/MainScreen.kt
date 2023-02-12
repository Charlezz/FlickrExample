package hello.com.pose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import hello.com.pose.composable.SearchBar
import hello.com.pose.shared.domain.photo.Photo

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val query: MutableState<String> = remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(8.dp)) {
            SearchBar(text = query.value,
                inputChange = {
                    query.value = it
                    viewModel.getImageList(query.value)
                })

            LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                viewModel.photos.value.data.let { photo ->
                    items(photo) {
                        MainContentItem(photo = it)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContentItem(photo: Photo) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(150.dp)
            .clickable(onClick = {

            })
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = photo.getStaticImageUrl(),
                imageLoader = ImageLoader.Builder(context = LocalContext.current)
                    .crossfade(true)
                    .build()
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}

