package com.sum3years.ui.main

import android.os.Environment
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sum3years.MainViewModel
import com.sum3years.model.PhotoUIModel
import com.sum3years.model.SearchDisplay
import com.sum3years.state.rememberSearchState
import java.io.File

@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onClick: (PhotoUIModel) -> Unit,
) {
    Column(modifier = modifier.widthIn(min = 100.dp)) {
        val context = LocalContext.current
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        val searchHistory = viewModel.searchHistory.collectAsStateWithLifecycle()
        val photoList = viewModel.photoList.collectAsStateWithLifecycle()
        val searchInProgress = viewModel.searchInProgress.collectAsStateWithLifecycle()
        val downloadedPhotos = viewModel.downloadedPhotos.collectAsStateWithLifecycle()

        val state = rememberSearchState(
            searchHistory = searchHistory.value,
            searchResult = photoList.value,
            searchInProgress = searchInProgress.value,
        )

        val dispatcher: OnBackPressedDispatcher =
            LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

        val backCallback = remember {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!state.focused) {
                        isEnabled = false
                        Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show()
                        dispatcher.onBackPressed()
                    } else {
                        state.query = TextFieldValue("")
                        state.focused = false
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                }
            }
        }

        DisposableEffect(dispatcher) { // dispose/relaunch if dispatcher changes
            dispatcher.addCallback(backCallback)
            onDispose {
                backCallback.remove() // avoid leaks!
            }
        }

        SearchBar(
            query = state.query,
            onQueryChange = { state.query = it },
            onSearch = {
                viewModel.search(it)
                focusManager.clearFocus()
            },
            onSearchFocusChange = { state.focused = it },
            onClearQuery = { state.query = TextFieldValue("") },
            onBack = { state.query = TextFieldValue("") },
            searching = state.searching,
            focused = state.focused,
            modifier = modifier,
        )

        when (state.searchDisplay) {
            SearchDisplay.Initial -> {
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    if (downloadedPhotos.value.isEmpty()) {
                        Text(
                            "다운로드 된 사진이 없습니다!",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    } else {
                        DownloadedPhotosScreen(downloadedPhotos)
                    }
                }
            }

            SearchDisplay.NoResults -> {
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("❌ 검색 결과가 없습니다!", fontSize = 24.sp, color = Color(0xffDD2C00))
                }
            }

            SearchDisplay.SearchHistory -> {
                state.searchInProgress = false
                SearchHistory(
                    histories = state.searchHistory,
                    onHistoryClick = { history ->
                        state.query = TextFieldValue(history)
                        viewModel.search(state.query.text)
                        focusManager.clearFocus()
                    },
                    onDelete = {
                        viewModel.deleteHistory(it)
                    },
                )
            }

            SearchDisplay.Results -> {
                PhotoListContent(
                    modifier = modifier,
                    photoList = state.searchResult,
                    onClick = onClick,
                    endOfList = { viewModel.search(query = state.query.text) },
                )
            }

            SearchDisplay.SearchInProgress -> {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun DownloadedPhotosScreen(downloadedPhotos: State<List<String>>) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        downloadedPhotos.value.forEach {
            AsyncImage(
                model = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),it),
                contentDescription = "Photo detail",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(text = it, modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth(), color = Color.White, textAlign = TextAlign.End)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}