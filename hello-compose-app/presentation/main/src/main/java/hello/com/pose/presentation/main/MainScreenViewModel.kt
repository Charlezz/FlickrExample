package hello.com.pose.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.assignment.core.mvi.MviReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.com.pose.shared.domain.photo.GetPhotoPagingSourceUseCase
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getPhotoPagingSourceUseCase: GetPhotoPagingSourceUseCase,
) : ViewModel() {

    private val reducer =
        MviReducer<MainContract.Event, MainContract.State, MainContract.Effect>(
            viewModelScope = viewModelScope,
            initialState = createInitialState(),
            handleEvent = ::handleEvent
        )

    val eventHandler = reducer::setEvent
    val stateFlow = reducer.stateFlow
    val effectFlow = reducer.effectFlow

    private fun createInitialState() = MainContract.State(MainContract.State.PhotoListState.Idle)

    private fun handleEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.DoSearchPhoto -> reducer.setState {
                copy(
                    photoListState = MainContract.State.PhotoListState.Success(
                        getPhotoPagingSourceUseCase(event.query)
                            .cachedIn(viewModelScope)
                    )
                )
            }
        }
    }
}