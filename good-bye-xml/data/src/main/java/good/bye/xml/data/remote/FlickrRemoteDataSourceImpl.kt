package good.bye.xml.data.remote

import android.util.Log
import com.example.network.api.FlickrApi
import com.example.network.model.photos.GetPhotosResponse
import com.example.network.model.NetworkResult
import javax.inject.Inject

class FlickrRemoteDataSourceImpl @Inject constructor(
    val flickrApi: FlickrApi
): FlickrRemoteDataSource {

    override suspend fun getPhotosForRecent(perPage: Int, page: Int): NetworkResult<GetPhotosResponse> {
        logging(perPage = perPage, page = page)
        return flickrApi.getPhotosForRecent(perPage, page)
    }

    override suspend fun getPhotosForSearch(keyword: String, perPage: Int, page: Int): NetworkResult<GetPhotosResponse> {
        logging(perPage = perPage, page = page, keyword = keyword)
        return flickrApi.getPhotosForSearch(keyword, perPage, page)
    }

    private fun logging(perPage: Int, page: Int, keyword: String? = null) {
        val type = keyword?.run { "Search" } ?: "Recent"
        Log.i("Flickr_API", "type [$type] perPage [$perPage] page [$page]")
    }
}