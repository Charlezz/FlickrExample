package hello.com.pose.shared.remote.service

import hello.com.pose.shared.data.BuildConfig
import hello.com.pose.shared.remote.response.FlickrResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {

    @GET("services/rest?nojsoncallback=1")
    suspend fun fetchFlickrSearchPhoto(
        @Query("api_key") key: String = BuildConfig.API_KEY,
        @Query("text") text: String,
        @Query("method") method: String = "flickr.photos.search",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20,
        @Query("format") format: String = "json",
    ): FlickrResponse
}