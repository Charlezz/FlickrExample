package kwon.dae.won.data.retrofit

import kwon.dae.won.data.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @author soohwan.ok
 */
object FlickrRetrofitFactory {

    private fun createOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder().apply {
                    val oldUrl = chain.request().url()
                    val newUrl = oldUrl
                        .newBuilder()
                        .addQueryParameter("api_key", BuildConfig.API_KEY)
                        .addQueryParameter("format","json") // Flickr API 는 별도로 이 쿼리 파라미터를 추가 하지 않으면 XML 포맷으로 응답을 내려 줌
                        .addQueryParameter("nojsoncallback","1") // 웹용, 1이면 무시
                        .build()
                    this.url(newUrl)
                }
                return@addInterceptor chain.proceed(requestBuilder.build())
            }
            .build()
    }

    fun create(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.flickr.com/services/rest/")
            .client(createOkhttpClient())
            .addCallAdapterFactory(FlickrCallAdapterFactory())
            .addConverterFactory(FlickrMoshiConverterFactory.create())
            .build()
    }

}