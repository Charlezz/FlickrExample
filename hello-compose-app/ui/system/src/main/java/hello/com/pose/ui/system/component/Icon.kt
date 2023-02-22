package hello.com.pose.ui.system.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import hello.com.pose.ui.system.R

@Composable
fun IconBack(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_arrow_back_24),
        contentDescription = null,
        modifier = modifier
            .padding(16.dp)
            .clickable { onClick() }
    )
}

@Composable
fun IconSetting(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_setting_24),
        contentDescription = null,
        modifier = modifier
            .padding(16.dp)
            .clickable { onClick() }
    )
}