package hello.com.pose.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import hello.com.pose.R

@Composable
fun SearchBar(
    text: String,
    inputChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = inputChange,
        enabled = true,
        singleLine = true,
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
        label = { Text(text = stringResource(id = R.string.search_bar_hint)) },
        modifier = Modifier.fillMaxWidth()
    )
}