package com.sum3years.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun <T> rememberSearchState(
    searchHistory: List<String> = emptyList(),
    searchResult: List<T> = emptyList(),
    searchInProgress: Boolean = false,
): SearchState<T> {
    return remember {
        SearchState(
            searchHistory,
            searchResult,
            searchInProgress,
        )
    }.also { state ->
        state.searchHistory = searchHistory
        state.searchResult = searchResult
    }
}
