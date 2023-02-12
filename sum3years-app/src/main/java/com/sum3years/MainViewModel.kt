package com.sum3years

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sum3years.data.model.FlickerException
import com.sum3years.domain.repository.FlickerRepository
import com.sum3years.model.PhotoUIModel
import com.sum3years.model.toUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: FlickerRepository,
) : ViewModel() {
    val searchHistory = MutableStateFlow<List<String>>(listOf("history1", "history2", "history3"))

    private var lastPage = 1
    private var loadFinished = false

    private val _photoList = MutableStateFlow<List<PhotoUIModel>>(emptyList())
    val photoList = _photoList.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch {
            val response = repository.getPhotos(
                query = query,
                page = lastPage,
                pageSize = PAGE_SIZE,
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
                        _errorMessage.value = errMsg
                    }
                }
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
