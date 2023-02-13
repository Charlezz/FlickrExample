package good.bye.xml.data.intercept

import android.util.Log
import com.ddd.pollpoll.datastore.PollPollDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class HttpRequestInterceptor(private val pollPreference: PollPollDataStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            pollPreference.getToken().first()
        }
        Log.e("token", token)

        val originalRequest = chain.request()
        val bearer = if ((originalRequest.url.toString()).contains("login")) "" else "Bearer "
        val request = originalRequest.newBuilder().url(originalRequest.url)
            .header("Authorization", "$bearer$token")
            .build()
        val response = chain.proceed(request)
        return response
    }
}
