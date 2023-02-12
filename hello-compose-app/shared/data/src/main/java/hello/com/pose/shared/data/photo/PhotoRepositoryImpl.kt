package hello.com.pose.shared.data.photo

import hello.com.pose.shared.domain.photo.Photo
import hello.com.pose.shared.domain.photo.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val remoteDataSource: PhotoRemoteDataSource
) : PhotoRepository {

    override suspend fun fetchSearchPhoto(query: String, page: Int): List<Photo> {
        return remoteDataSource.fetchFlickrSearchPhoto(query, page).map { it.toDomain() }
    }
}