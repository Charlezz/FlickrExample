package hello.com.pose.shared.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hello.com.pose.shared.remote.BuildConfig
import hello.com.pose.shared.remote.service.FlickrService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient() = OkHttpClient
        .Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        )
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideFlickrService(client: OkHttpClient): FlickrService {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .baseUrl("https://api.flickr.com/")
            .build()
            .create(FlickrService::class.java)
    }
}