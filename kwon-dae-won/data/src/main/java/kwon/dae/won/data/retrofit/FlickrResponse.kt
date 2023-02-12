package kwon.dae.won.data.retrofit

import java.io.IOException

/**
 * @author soohwan.ok
 */
sealed class FlickrResponse<out T> {
    data class Success<T>(val value: T?) : FlickrResponse<T>()

    data class Failure(val code: Int?, val message: String?) : FlickrResponse<Nothing>()

    data class NetworkError(val exception: IOException) : FlickrResponse<Nothing>()

    data class Unexpected(val t: Throwable?) : FlickrResponse<Nothing>()
}