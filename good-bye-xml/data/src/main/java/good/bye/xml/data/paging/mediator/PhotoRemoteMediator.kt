package good.bye.xml.data.paging.mediator

import android.accounts.NetworkErrorException
import androidx.paging.*
import com.example.network.datasource.FlickrRemoteDataSource
import com.example.network.model.NetworkResult
import com.example.network.model.photos.PhotoResponse
import good.bye.xml.data.mapper.toLocal
import good.bye.xml.local.room.datasource.FlickrLocalDataSource
import good.bye.xml.local.room.model.PhotoEntity

@OptIn(ExperimentalPagingApi::class)
class PhotoRemoteMediator(
    private val keyword: String?,
    private val remote: FlickrRemoteDataSource,
    private val local: FlickrLocalDataSource,
): RemoteMediator<Int, PhotoEntity>() {

    companion object {
        private const val START_PAGE = 1
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>
    ): MediatorResult {
        try {
            val loadPage = when (loadType) {
                // Refresh - 데이터 페이징 처음 시작 의미
                LoadType.REFRESH ->
                    START_PAGE

                // Prepend - 데이터 페이징 처음 시작 의미
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)

                // Append - 로컬 DB 내 저장된 다음 페이지
                LoadType.APPEND ->
                    local.getNextPage()
            }

            val perPage = state.config.pageSize

            val response = when {
                // keyword 입력이 비어있다면 recent
                keyword.isNullOrBlank() ->
                    remote.getPhotosForRecent(page = loadPage, perPage = perPage)

                // keyword 입력이 유효하다면 search
                else ->
                    remote.getPhotosForSearch(keyword = keyword, page = loadPage, perPage = perPage)
            }

            val (photos, nextPage) = when (response) {
                is NetworkResult.Error ->
                    throw NetworkErrorException("[${response.code}] ${response.message}")

                is NetworkResult.Exception ->
                    throw response.exception

                is NetworkResult.Success ->
//                    response.data.photos.run { photo.map(PhotoResponse::toDomain) to page.plus(1) }
                    response.data.photos.run { photo.map(PhotoResponse::toLocal) to page.plus(1) }
            }

            when (loadType) {
                LoadType.REFRESH ->
                    local.clearToAddPhotos(photos, nextPage)

                LoadType.APPEND ->
                    local.addPhotos(photos, nextPage)

                else -> {}
            }

            // 페이징 종료 여부 - Remote에서 받아온 데이터가 비어있다면 페이징 종료
            val endOfPaginationReached = photos.isEmpty()

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}