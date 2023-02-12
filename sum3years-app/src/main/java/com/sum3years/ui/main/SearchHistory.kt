package com.sum3years.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchHistory(
    modifier: Modifier = Modifier,
    histories: List<String>,
    onHistoryClick: (String) -> Unit,
) {
    LazyColumn(modifier = modifier.padding(4.dp)) {
        items(histories) { history ->
            Text(
                text = history,
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { onHistoryClick(history) },
            )
        }
    }
}

@Preview
@Composable
fun SearchHistoryPreview() {
    val histories = listOf("History1", "History2", "History3")
    SearchHistory(histories = histories, onHistoryClick = {})
}
