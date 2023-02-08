package hello.com.pose.shared.remote.photo

import hello.com.pose.shared.data.photo.PhotoData
import hello.com.pose.shared.data.photo.PhotoRemoteDataSource
import hello.com.pose.shared.remote.service.FlickrService
import javax.inject.Inject

class PhotoRemoteDataSourceImpl @Inject constructor(
    private val service: FlickrService
) : PhotoRemoteDataSource {

    override suspend fun fetchFlickrSearchPhoto(query: String, page: Int): List<PhotoData> {
        return service.fetchFlickrSearchPhoto(
            text = query,
            page = page
        ).photos?.photo?.map { it.toData() }.orEmpty()
    }
}