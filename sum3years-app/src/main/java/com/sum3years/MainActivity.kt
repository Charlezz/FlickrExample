package com.sum3years

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showPhotoDetail by remember { mutableStateOf<PhotoUIModel?>(null) }

            val snackbarHostState = remember { SnackbarHostState() }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                ) { paddingValues ->
                    MainScreen(
                        modifier = Modifier.padding(
                            bottom = paddingValues.calculateBottomPadding(),
                            top = paddingValues.calculateTopPadding(),
                        ),
                        viewModel = viewModel,
                    ) { photo ->
                        showPhotoDetail = photo
                    }
                }
            }
            showPhotoDetail?.let { photo ->
                Dialog(onDismissRequest = { showPhotoDetail = null }) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        PhotoDetail(
                            photo = photo,
                            onClose = { showPhotoDetail = null },
                            onDownloadClick = {
                                viewModel.downloadFile(url = photo.loadUrlMedium, fileName = photo.fileName, this)
                                Toast.makeText(
                                    this,
                                    "Download ${photo.loadUrlMedium}",
                                    Toast.LENGTH_SHORT,
                                ).show()
//                                Log.e("download", "url: ${photo.loadUrlMedium}")
                            },
                        )
                    }
                }
            }

            LaunchedEffect(Unit) {
                launch {
                    viewModel.errorMessage.collect { errMsg ->
                        snackbarHostState.showSnackbar(
                            errMsg,
                            duration = SnackbarDuration.Indefinite,
                            withDismissAction = true,
                        )
                    }
                }
            }
        }
    }
}
