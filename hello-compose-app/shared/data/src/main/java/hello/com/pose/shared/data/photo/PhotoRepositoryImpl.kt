package hello.com.pose.shared.data.photo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import hello.com.pose.shared.domain.photo.Photo
import hello.com.pose.shared.domain.photo.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PhotoRepositoryImpl @Inject constructor(
    private val photoRemoteDataSource: PhotoRemoteDataSource,
    private val photoLocalDataSource: PhotoLocalDataSource,
) : PhotoRepository {

    override suspend fun fetchSearchPhoto(query: String, page: Int): List<Photo> {
        return photoRemoteDataSource.fetchFlickrSearchPhoto(query, page).map { it.toDomain() }
    }

    override fun getPhotoPagingSourceFlow(query: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            remoteMediator = PhotoRemoteMediator(
                query = query,
                photoRemoteDataSource = photoRemoteDataSource,
                photoLocalDataSource = photoLocalDataSource,
            ),
            pagingSourceFactory = { photoLocalDataSource.getPhotoPagingSource() },
        ).flow.map { pagingData -> pagingData.map { it.toData().toDomain() } }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
