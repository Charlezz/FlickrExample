package com.sum3years.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchHistory(
    modifier: Modifier = Modifier,
    histories: List<String>,
    onHistoryClick: (String) -> Unit,
    onDelete: (String) -> Unit,
) {
    LazyColumn(modifier = modifier.padding(4.dp)) {
        items(histories) { history ->
            SearchHistoryItem(
                history = history,
                onItemClick = onHistoryClick,
                onDelete = onDelete,
            )
        }
    }
}

@Composable
fun SearchHistoryItem(
    history: String,
    onItemClick: (String) -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = history,
            modifier = Modifier.padding(start = 8.dp).weight(1f).clickable { onItemClick(history) },
        )
        IconButton(
            onClick = { onDelete(history) },
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "delete search history button",
            )
        }
    }
}

@Preview
@Composable
fun SearchHistoryPreview() {
    val histories = listOf("History1", "History2", "History3")
    SearchHistory(histories = histories, onHistoryClick = {}, onDelete = {})
}
