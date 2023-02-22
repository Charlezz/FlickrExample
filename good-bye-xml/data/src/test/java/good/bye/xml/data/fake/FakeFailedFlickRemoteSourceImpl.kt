package good.bye.xml.data.fake

import com.example.network.model.NetworkResult
import com.example.network.model.photos.GetPhotosResponse
import good.bye.xml.data.remote.FlickrRemoteDataSource

class FakeFailedFlickRemoteSourceImpl() : FlickrRemoteDataSource {

    val fakeFailed = NetworkResult.Error(
        500,
        "테스트테스트"
    )

    override suspend fun getPhotosForRecent(
        perPage: Int,
        page: Int
    ): NetworkResult<GetPhotosResponse> {
        return fakeFailed
    }

    override suspend fun getPhotosForSearch(
        keyword: String,
        perPage: Int,
        page: Int
    ): NetworkResult<GetPhotosResponse> = fakeFailed
}
