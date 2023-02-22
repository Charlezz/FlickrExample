package com.sum3years

import android.os.Bundle
import android.os.Environment
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
import androidx.lifecycle.lifecycleScope
import com.sum3years.model.PhotoUIModel
import com.sum3years.ui.main.MainScreen
import com.sum3years.ui.main.PhotoDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

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
                                downloadImage(photo) { success ->
                                    // TODO 여기서 lifecycleScope 열어도 되나? 람다에서는 리컴포지션 영향 없나?
                                    lifecycleScope.launch(Dispatchers.Main) {
                                        val message =
                                            if (success) "Download 폴더에 저장되었습니다!" else "다운로드에 실패했습니다"
                                        Toast.makeText(
                                            this@MainActivity,
                                            message,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }
                                }
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

    private fun downloadImage(
        image: PhotoUIModel,
        success: (Boolean) -> Unit = {},
    ) {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                val connection = URL(image.loadUrlMedium).openConnection() as HttpURLConnection
                val inputStream = connection.inputStream

                val downloadDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadDir.exists()) {
                    downloadDir.mkdir()
                }
                val file = File(downloadDir, "${image.id}_${image.title}.jpg")

                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                outputStream.flush()
                outputStream.close()

                success(file.exists())
            }
        } catch (_: Exception) {
            success(false)
        }
    }
}
