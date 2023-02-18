package hello.com.pose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class StateViewModel<T> : ViewModel() {
    private val _stateFlow: MutableStateFlow<T> = MutableStateFlow(initState())
    val stateFlow: StateFlow<T> = _stateFlow.asStateFlow()
    var state: T
        set(value) {
            _stateFlow.value = value
        }
        get() = _stateFlow.value


    abstract fun initState(): T

    fun updateState(block: T.() -> T) {
        state = block(state)
    }
}