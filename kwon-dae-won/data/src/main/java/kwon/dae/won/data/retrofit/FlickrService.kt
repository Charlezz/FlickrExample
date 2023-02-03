package kwon.dae.won.data.retrofit

import kwon.dae.won.data.model.PhotosDTO
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author soohwan.ok
 */
interface FlickrService {

    @GET("?method=flickr.photos.getRecent")
    suspend fun getRecent(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): FlickrResponse<PhotosDTO>

    @GET("?method=flickr.photos.search")
    suspend fun search(
        @Query("text") text:String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ):FlickrResponse<PhotosDTO>
}