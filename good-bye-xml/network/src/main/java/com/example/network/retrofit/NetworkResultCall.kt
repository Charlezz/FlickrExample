package com.example.network.retrofit

import com.example.network.model.NetworkResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResultCall<T: Any>(
    private val proxy: Call<T>
): Call<NetworkResult<T>> {

    override fun enqueue(callback: Callback<NetworkResult<T>>) {
        proxy.enqueue(
            object: Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val code = response.code()
                    val body = response.body()

                    val networkResult = when {
                        response.isSuccessful && body != null ->
                            NetworkResult.Success(body)

                        else ->
                            NetworkResult.Error(code = code, message = response.message())
                    }

                    callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    val networkResult = NetworkResult.Exception(t)
                    callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
                }
            }
        )
    }

    override fun execute(): Response<NetworkResult<T>> = throw NotImplementedError()

    override fun clone(): Call<NetworkResult<T>> = NetworkResultCall(proxy.clone())

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun cancel() = proxy.cancel()
}