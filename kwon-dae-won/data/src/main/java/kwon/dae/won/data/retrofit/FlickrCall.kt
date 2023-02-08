package kwon.dae.won.data.retrofit

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * @author soohwan.ok
 */
class FlickrCall<T:FlickrResponse<*>> constructor(
    private val call: Call<T>
) : Call<T> {
    override fun clone(): Call<T> = FlickrCall(call.clone())

    override fun execute(): Response<T> {
        throw UnsupportedOperationException("FlickrCall은 execute()를 지원 하지 않아요!")
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()

    override fun enqueue(callback: Callback<T>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val response: Response<T> = Response.success(response.body())
                callback.onResponse(
                    this@FlickrCall,
                    response
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResponse:T = when (t) {
                    is IOException -> FlickrResponse.NetworkError(t)
                    else -> FlickrResponse.Unexpected(t)
                } as T
                callback.onResponse(this@FlickrCall, Response.success(networkResponse))
            }
        })
    }
}