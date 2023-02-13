package com.sum3years

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sum3years.data.model.FlickerException
import com.sum3years.domain.repository.FlickerRepository
import com.sum3years.model.PhotoUIModel
import com.sum3years.model.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: FlickerRepository,
) : ViewModel() {

    val searchHistory = repository.loadSearchHistories().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList(),
    )

    private var lastPage = 1
    private var loadFinished = false

    private val _photoList = MutableStateFlow<List<PhotoUIModel>>(emptyList())
    val photoList = _photoList.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val previousQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            delay(3000)
            _errorMessage.emit("TEST: 에러 발생!!") // TODO remove this
        }
    }

    fun search(query: String) {
        if (query.trim().isBlank()) return
        if (query != previousQuery.value) {
            resetResult()
            previousQuery.value = query
        }
        viewModelScope.launch {
            val response = repository.getPhotos(
                query = query,
                page = lastPage,
                pageSize = if (lastPage == 1) PAGE_SIZE * 3 else PAGE_SIZE,
            )
            if (response.isSuccess) {
                response.getOrNull()?.let {
                    val newData = it.map { photo -> photo.toUIModel() }
                    Log.d("로그", "MainViewModel_search: newData = $newData")
                    if (newData.isNotEmpty()) {
                        _photoList.value = _photoList.value + newData
                        Log.d(
                            "로그",
                            "MainViewModel_search: size: ${photoList.value.size}, photoList = ${photoList.value}",
                        )
                        lastPage++
                    } else {
                        loadFinished = true
                    }
                }
            } else {
                response.exceptionOrNull()?.let { exception ->
                    (exception as? FlickerException)?.message?.let { errMsg ->
                        _errorMessage.emit(errMsg)
                    }
                }
            }
        }
        insertHistory(query)
    }

    fun insertHistory(history: String) {
        viewModelScope.launch {
            repository.insertSearchHistory(history)
        }
    }

    fun deleteHistory(history: String) {
        viewModelScope.launch {
            repository.deleteSearchHistory(history)
        }
    }

    private fun resetResult() {
        _photoList.value = emptyList()
        lastPage = 1
        loadFinished = false
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
