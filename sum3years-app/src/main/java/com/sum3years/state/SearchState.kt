package com.sum3years.state

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.sum3years.model.SearchDisplay

class SearchState<T>(
    searchHistory: List<String>,
    searchResult: List<T>,
) {
    var query by mutableStateOf(TextFieldValue())
    var focused by mutableStateOf(false)
    var searchHistory by mutableStateOf(searchHistory)
    var searchResult by mutableStateOf(searchResult)
    var previousQueryText = ""
        private set
    var searching by mutableStateOf(false)
    var searchInProgress = searching

    init {
        Log.d("로그", "SearchState_searchResult: $searchResult")
        Log.d("로그", "SearchState_searchResult: ${this.searchResult}")
    }

    val searchDisplay: SearchDisplay
        get() = when {
            !focused && query.text.isEmpty() -> SearchDisplay.Initial
            searchInProgress -> SearchDisplay.SearchInProgress
            focused && query.text.isEmpty() -> SearchDisplay.SearchHistory
            searchResult.isEmpty() -> {
                previousQueryText = query.text
                SearchDisplay.NoResults
            }
            else -> {
                previousQueryText = query.text
                SearchDisplay.Results
            }
        }

    fun sameAsPreviousQuery() = query.text == previousQueryText
}
