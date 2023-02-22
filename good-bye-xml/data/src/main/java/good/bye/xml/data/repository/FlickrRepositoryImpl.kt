package good.bye.xml.data.repository

import androidx.paging.*
import com.example.network.datasource.FlickrRemoteDataSource
import good.bye.xml.data.mapper.toDomain
import good.bye.xml.data.paging.mediator.PhotoRemoteMediator
import good.bye.xml.data.paging.source.PhotoPagingSourceRecent
import good.bye.xml.data.paging.source.PhotoPagingSourceSearch
import good.bye.xml.domain.model.photo.Photo
import good.bye.xml.domain.repository.FlickrRepository
import good.bye.xml.local.room.datasource.FlickrLocalDataSource
import good.bye.xml.local.room.model.PhotoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FlickrRepositoryImpl @Inject constructor(
    val remote: FlickrRemoteDataSource,
    val local: FlickrLocalDataSource
) : FlickrRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun loadPhotosForPaging(keyword: String?, perPage: Int): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = perPage),
            remoteMediator = PhotoRemoteMediator(keyword = keyword, remote = remote, local = local),
            pagingSourceFactory = { local.getPhotoPagingSource() }
        )
            .flow
            .map { it.map(PhotoEntity::toDomain) }
    }

    override suspend fun getPhotosForRecent(perPage: Int, page: Int): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = perPage),
            pagingSourceFactory = { PhotoPagingSourceRecent(remote) }
        ).flow
    }

    override suspend fun getPhotosForSearch(keyword: String, perPage: Int, page: Int): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = perPage),
            pagingSourceFactory = { PhotoPagingSourceSearch(dataSource = remote, keyword = keyword) }
        ).flow
    }

    /** Validation Check
     * @param page 1~500 범위 내 값이 아닐 경우 에러 반환
     * - 0 이하는 1로 처리 or 아무리 많은 값 입력 시 perPage 500으로 내려오는 점 확인
     * - 1..500 범위 외 요청 시 정상 응답으로, 원하는 동작으로 인식이 가능해 에러 반환
     *
     * @param perPage 0 이하 입력 시 에러 반환
     * - page 0 이하 입력 시 응답 오류는 없지만, `page=1`로 처리되어 정상 응답이 내려오는 점 확인
     * - 0 이하 입력 시 입력값에 맞는 응답이 아니기에, 에러 반환으로 오류 파악이 나을 것 같아 처리
     * */
    private fun validationCheck(perPage: Int, page: Int) {
        if (page <= 0) {
            throw IllegalArgumentException("Smaller than the minimum")
        }

        if (perPage !in 1..500) {
            throw IllegalArgumentException("Over the minimum or maximum range")
        }
    }
}
