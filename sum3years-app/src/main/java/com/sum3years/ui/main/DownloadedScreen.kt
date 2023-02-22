package com.sum3years.ui.main

import android.os.Environment
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DownloadedScreen() {
    val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val images = downloadDir.listFiles { file -> file.isFile && file.extension == "jpg" }
    var deleteTarget: File? by remember { mutableStateOf(null) }

    if (images != null && images.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(images.size) { index ->
                val imageFile = images[images.lastIndex - index]
                AsyncImage(
                    model = imageFile,
                    contentDescription = "downloaded image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().combinedClickable(
                        onClick = { },
                        onLongClick = {
                            deleteTarget = imageFile
                        },
                    ),
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                "다운로드 된 사진이 없습니다!",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }

    deleteTarget?.let { file ->
        AlertDialog(
            onDismissRequest = { deleteTarget = null },
            title = {
                Text(text = "정말 삭제하시겠습니까?")
            },
            text = {
                Text(text = "저장한 사진이 삭제됩니다.")
            },
            confirmButton = {
                TextButton(onClick = {
                    file.delete()
                    deleteTarget = null
                }) {
                    Text("삭제")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    deleteTarget = null
                }) {
                    Text("취소")
                }
            },
            shape = RoundedCornerShape(8.dp),
        )
    }
}
