package good.bye.xml.domain.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface ResultWrapper<out T> {

    data class Success<out T>(val data: T): ResultWrapper<T>

    data class Error(val exception: Throwable? = null): ResultWrapper<Nothing>

    data class NetworkError(val code: Int, val message: String? = null): ResultWrapper<Nothing>

    object Loading : ResultWrapper<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<ResultWrapper<T>> {
    return this
        .map<T, ResultWrapper<T>> { data -> ResultWrapper.Success(data) }
        .onStart { emit(ResultWrapper.Loading) }
        .catch { emit(ResultWrapper.Error(it)) }
}