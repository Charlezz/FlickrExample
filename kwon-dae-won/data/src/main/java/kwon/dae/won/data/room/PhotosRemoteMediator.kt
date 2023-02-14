package kwon.dae.won.data.room

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kwon.dae.won.data.mapper.PhotoDtoMapper
import kwon.dae.won.domain.model.Photo
import kwon.dae.won.domain.repository.FlickrRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Daewon on 09,February,2023
 *
 */
const val PER_PAGE = 30

@OptIn(ExperimentalPagingApi::class)
class PhotosRemoteMediator @Inject constructor(
    private val flickrRepository: FlickrRepository,
    private val photosDatabase: PhotosDatabase,
) : RemoteMediator<Int, Photo>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (photosDatabase.getPhotosDao().getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, Photo>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                // DB 클리어
                val photo = getRemoteKeyClosestToCurrentPosition(state)
                photo?.page ?: 1
            }
            LoadType.PREPEND -> {
                val photo = getRemoteKeyForFirstItem(state)
                // photo 가 null 이라면 새로운 데이터가 DB에 저장되지 않음
                val prevKey = if (photo?.page == 1) null else photo?.page?.minus(1)
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = photo != null)
            }
            LoadType.APPEND -> {
                val photo = getRemoteKeyForLastItem(state)
                // photo 가 null 이라면 새로운 데이터가 DB에 저장되지 않음
                // photo != null , nextKey == null -> 끝 페이지를 의미
                val nextKey = if (photo?.page == photo?.maxPage) null else photo?.page?.plus(1)
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = photo != null)
            }
        }

        runCatching {
            val apiResponse = flickrRepository.getRecent(perPage = PER_PAGE, page = page)

            apiResponse.onSuccess { photos ->
                val endOfPaginationReached = photos.isEmpty()

                photosDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        photosDatabase.getPhotosDao().clearAllPhotos()
                    }
                    photosDatabase.getPhotosDao().insertAll(photos.map { photo ->
                        PhotoDtoMapper.from(photo)
                    })
                }
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }.onFailure {
                MediatorResult.Error(it)
            }
        }.onFailure {
            MediatorResult.Error(it)
        }
        return MediatorResult.Success(endOfPaginationReached = false)
    }


    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Photo>): Photo? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, Photo>): Photo? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, Photo>): Photo? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()
    }
}
