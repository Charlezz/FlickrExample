package hello.com.pose.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.com.pose.shared.domain.photo.GetPhotoPagingSourceUseCase
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getPhotoPagingSourceUseCase: GetPhotoPagingSourceUseCase,
) : ViewModel() {

    fun getPagingFlow(query: String) = getPhotoPagingSourceUseCase(query)
        .cachedIn(viewModelScope)

    fun onDoneSearch(query: String) {

    }
}