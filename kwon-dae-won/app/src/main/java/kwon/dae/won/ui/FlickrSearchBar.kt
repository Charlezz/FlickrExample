package kwon.dae.won.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import kwon.dae.won.R

/**
 * @author Daewon on 20,February,2023
 *
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    text: String,
    onValueChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val leadingIcon = @Composable {
        IconButton(
            onClick = {
                onSearchClick()
                keyboardController?.hide()
            },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_search_24),
                contentDescription = null
            )
        }
    }
    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                onCancelClick()
            },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_cancel_24),
                contentDescription = null
            )
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.weight(1f),
            value = text,
            onValueChange = { onValueChange(it) },
            singleLine = true,
            leadingIcon = if (text.isNotBlank()) leadingIcon else null,
            trailingIcon = if (text.isNotBlank()) trailingIconView else null,
            placeholder = {
                Text(text = "검색어를 입력하세요(예: 사진)", color = Color.Gray)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick()
                    focusManager.clearFocus()
                }
            )
        )
    }
}
