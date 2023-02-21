package hello.com.pose.presentation.main

import androidx.paging.PagingData
import com.assignment.core.mvi.UiEffect
import com.assignment.core.mvi.UiEvent
import com.assignment.core.mvi.UiState
import hello.com.pose.shared.domain.photo.Photo
import kotlinx.coroutines.flow.Flow

interface MainContract {
    sealed interface Event : UiEvent {
        data class DoSearchPhoto(val query: String) : Event
    }

    data class State(
        val photoListState: PhotoListState
    ) : UiState {
        sealed interface PhotoListState {
            object Idle : PhotoListState
            data class Success(
                val pagingDataFlow: Flow<PagingData<Photo>>
            ) : PhotoListState
        }
    }

    sealed interface Effect : UiEffect
}
