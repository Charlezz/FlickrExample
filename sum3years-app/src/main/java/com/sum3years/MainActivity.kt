package com.sum3years

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sum3years.data.datasource.FlickerRemoteDataSource
import com.sum3years.data.network.FlickerService
import com.sum3years.domain.repository.FlickerRepositoryImpl
import com.sum3years.ui.main.MainScreen
import com.sum3years.ui.theme.FlickrExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
            val viewModel: MainViewModel = viewModel(viewModelStoreOwner) {
                val flickerService = FlickerService.flickerService
                val dataSource = FlickerRemoteDataSource(flickerService)
                val repository = FlickerRepositoryImpl(dataSource)
                MainViewModel(repository)
            }
            val errorMessage = viewModel.errorMessage.collectAsState()

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                Scaffold { paddingValues ->
                    MainScreen(
                        modifier = Modifier.padding(
                            bottom = paddingValues.calculateBottomPadding(),
                            top = paddingValues.calculateTopPadding(),
                        ),
                        viewModel = viewModel,
                    ) { photo ->
                        // TODO Download Image
                        Toast.makeText(
                            this,
                            "Download ${photo.loadUrlOriginal}",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                    if (errorMessage.value.isNotBlank()) {
                        Snackbar {
                            Text(text = errorMessage.value)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FlickrExampleTheme {
        Greeting("Android")
    }
}
