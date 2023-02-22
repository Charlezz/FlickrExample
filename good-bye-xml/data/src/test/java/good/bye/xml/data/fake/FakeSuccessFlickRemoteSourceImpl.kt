package good.bye.xml.data.fake

import com.example.network.model.NetworkResult
import com.example.network.model.photos.GetPhotosResponse
import com.example.network.model.photos.PhotosResponse
import good.bye.xml.data.FakePhoto
import good.bye.xml.data.datasource.local.remote.FlickrRemoteDataSource

class FakeSuccessFlickRemoteSourceImpl() : FlickrRemoteDataSource {

    val fakeSuccess = NetworkResult.Success(
        GetPhotosResponse(
            photos = PhotosResponse(
                page = 0,
                totalPages = 20,
                perpage = 20,
                total = 5,
                photo = listOf(
                    FakePhoto.testPhoto1,
                    FakePhoto.testPhoto2

                )
            ),
            stat = ""
        )
    )

    override suspend fun getPhotosForRecent(
        perPage: Int,
        page: Int
    ): NetworkResult<GetPhotosResponse> {
        return fakeSuccess
    }

    override suspend fun getPhotosForSearch(
        keyword: String,
        perPage: Int,
        page: Int
    ): NetworkResult<GetPhotosResponse> = fakeSuccess
}
