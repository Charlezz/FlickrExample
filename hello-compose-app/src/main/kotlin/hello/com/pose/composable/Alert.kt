package hello.com.pose.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import hello.com.pose.MainViewModel
import hello.com.pose.R
import hello.com.pose.shared.domain.photo.Photo

@Composable
fun Alert(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    photo: Photo,
    viewModel: MainViewModel = viewModel()
) {
    if (showDialog) {
        val state by viewModel.downloadStateFlow.collectAsState()

        AlertDialog(
            containerColor = Color.White,
            title = { Text(text = stringResource(id = R.string.download_title)) },
            text = { Text(text = stringResource(id = R.string.download_sub_title)) },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    viewModel.saveFile(photo.getStaticImageUrl(), photo)
                }) {
                    Text(text = stringResource(id = R.string.confirm), fontSize = 16.sp)
                }
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(size = 64.dp)
                            .fillMaxWidth(),
                        color = Color.Magenta,
                        strokeWidth = 6.dp
                    )
                }
                if (state.isCompleted) {
                    state.isCompleted = false
                    onDismiss.invoke()
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss.invoke() }) {
                    Text(text = stringResource(id = android.R.string.cancel), fontSize = 16.sp)
                }
            },
            shape = RoundedCornerShape(24.dp)
        )
    }
}