package hello.com.pose.presentation.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hello.com.pose.shared.domain.setting.AppTheme
import hello.com.pose.shared.domain.setting.DarkThemeConfig
import hello.com.pose.ui.system.component.TopNavigationBar

@Composable
fun SettingScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel()
) {

    val uiState by viewModel.settingsUiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopNavigationBar(
            titleRes = R.string.setting,
            onNavigateClick = onNavigateBack
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Panel(
                uiState = uiState,
                onChangeDarkThemeConfig = viewModel::updateDarkThemeConfig,
                onChangeAppTheme = viewModel::updateAppTheme
            )
        }
    }
}

@Composable
private fun SectionTitle(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun Panel(
    uiState: SettingUiState,
    onChangeDarkThemeConfig: (DarkThemeConfig) -> Unit,
    onChangeAppTheme: (AppTheme) -> Unit
) {
    when (uiState) {
        is SettingUiState.Loading -> {}
        is SettingUiState.Success -> {
            Column(
                Modifier.selectableGroup()
            ) {
                SectionTitle("Dark mode")
                ThemeSelectRow(
                    text = "System",
                    selected = uiState.setting.darkThemeConfig == DarkThemeConfig.SYSTEM,
                    onClick = { onChangeDarkThemeConfig(DarkThemeConfig.SYSTEM) }
                )
                ThemeSelectRow(
                    text = "Light",
                    selected = uiState.setting.darkThemeConfig == DarkThemeConfig.LIGHT,
                    onClick = { onChangeDarkThemeConfig(DarkThemeConfig.LIGHT) }
                )
                ThemeSelectRow(
                    text = "Dark",
                    selected = uiState.setting.darkThemeConfig == DarkThemeConfig.DARK,
                    onClick = { onChangeDarkThemeConfig(DarkThemeConfig.DARK) }
                )
            }

            Column(
                Modifier.selectableGroup()
            ) {
                SectionTitle("Theme")
                ThemeSelectRow(
                    text = "Default",
                    selected = uiState.setting.appTheme == AppTheme.DEFAULT,
                    onClick = { onChangeAppTheme(AppTheme.DEFAULT) }
                )
                ThemeSelectRow(
                    text = "Monstera",
                    selected = uiState.setting.appTheme == AppTheme.MONSTERA,
                    onClick = { onChangeAppTheme(AppTheme.MONSTERA) }
                )
                ThemeSelectRow(
                    text = "Carrot",
                    selected = uiState.setting.appTheme == AppTheme.CARROT,
                    onClick = { onChangeAppTheme(AppTheme.CARROT) }
                )
            }
        }
    }
}

@Composable
fun ThemeSelectRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}