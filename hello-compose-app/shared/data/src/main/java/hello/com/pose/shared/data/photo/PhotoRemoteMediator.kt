package hello.com.pose.shared.data.photo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator

@OptIn(ExperimentalPagingApi::class)
class PhotoRemoteMediator(
    private val query: String,
    private val photoRemoteDataSource: PhotoRemoteDataSource,
    private val photoLocalDataSource: PhotoLocalDataSource,
) : RemoteMediator<Int, PhotoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>,
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> FIRST_PAGE
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> photoLocalDataSource.getNextPage()
            }

            val photos = photoRemoteDataSource.fetchFlickrSearchPhoto(query, page)

            when (loadType) {
                LoadType.REFRESH -> photoLocalDataSource.setPhotoList(photos, FIRST_PAGE.plus(1))
                LoadType.APPEND -> photoLocalDataSource.addPhotoList(photos, page.plus(1))
                else -> {}
            }

            MediatorResult.Success(endOfPaginationReached = photos.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}
