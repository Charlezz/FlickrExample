package com.sum3years

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sum3years.model.PhotoUIModel
import com.sum3years.ui.main.MainScreen
import com.sum3years.ui.main.PhotoDetail
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val errorMessage = viewModel.errorMessage.collectAsState()

            var showPhotoDetail by remember { mutableStateOf<PhotoUIModel?>(null) }

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
                        showPhotoDetail = photo
                    }
                    if (errorMessage.value.isNotBlank()) {
                        Snackbar {
                            Text(text = errorMessage.value)
                        }
                    }
                }
            }
            showPhotoDetail?.let { photo ->
                Dialog(onDismissRequest = { showPhotoDetail = null }) {
                    Surface(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        PhotoDetail(
                            photo = photo,
                            onClose = { showPhotoDetail = null },
                            onDownloadClick = {
                                // TODO Download Image
                                Toast.makeText(
                                    this,
                                    "Download ${photo.loadUrlOriginal}",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            },
                        )
                    }
                }
            }
        }
    }
}
