package kwon.dae.won.data.room

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kwon.dae.won.data.mapper.PhotoDtoMapper
import kwon.dae.won.data.model.RemoteKeys
import kwon.dae.won.domain.model.Photo
import kwon.dae.won.domain.repository.FlickrRepository
import javax.inject.Inject

/**
 * @author Daewon on 09,February,2023
 *
 */
const val PER_PAGE = 20

@OptIn(ExperimentalPagingApi::class)
class PhotosRemoteMediator @Inject constructor(
    private val flickrRepository: FlickrRepository,
    private val photosDatabase: PhotosDatabase,
) : RemoteMediator<Int, Photo>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Photo>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                // DB 클리어
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // remoteKeys 가 null 이라면 새로운 데이터가 DB에 저장되지 않음
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // remotekeys 가 null 이라면 새로운 데이터가 DB에 저장되지 않음
                // remoteKeys != null , nextKey == null -> 끝 페이지를 의미
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        runCatching {
            val apiResponse = flickrRepository.getRecent(perPage = PER_PAGE, page = page)

            apiResponse.onSuccess { photos ->
                val endOfPaginationReached = photos.isEmpty()

                photosDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        photosDatabase.getRemoteKeysDao().clearAllPhotos()
                        photosDatabase.getPhotosDao().clearAllPhotos()
                    }
//                    val prevKey = if (page > 1) page.minus(1) else null
                    val prevKey = if (page == 1) null else page -1
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val remoteKeys = photos.map {
                        RemoteKeys(
                            photoId = it.id,
                            prevKey = prevKey,
                            currentPage = page,
                            nextKey = nextKey
                        )
                    }

                    photosDatabase.getRemoteKeysDao().insertAll(remoteKeys)
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


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Photo>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                photosDatabase.getRemoteKeysDao().getRemoteKeyByPhotoId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Photo>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { photo ->
            photosDatabase.getRemoteKeysDao().getRemoteKeyByPhotoId(photo.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Photo>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { photo ->
            photosDatabase.getRemoteKeysDao().getRemoteKeyByPhotoId(photo.id)
        }
    }
}
