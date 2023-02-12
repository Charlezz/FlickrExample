package good.bye.xml.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FlickrSearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
            .padding(10.dp, 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            value = text,
            singleLine = true,
            onValueChange = onTextChange,
            placeholder = {
                Text(
                    text = "검색어(예: 자연)",
                    color = Color.Black
                )
            }
        )

        Button(
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            onClick = { onSearchClick() },
            modifier = Modifier.fillMaxHeight(),
        ) {
            Text(text = "검색")
        }
    }
}
