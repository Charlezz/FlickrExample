package kwon.dae.won.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kwon.dae.won.data.retrofit.FlickrRetrofitFactory
import kwon.dae.won.data.retrofit.FlickrService
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesRetrofit(): Retrofit {
        return FlickrRetrofitFactory.create()
    }

    @Provides
    fun providesFlickrService(retrofit: Retrofit):FlickrService{
        return retrofit.create(FlickrService::class.java)
    }

}