package good.bye.xml.local.room.datasource

import androidx.paging.PagingSource
import androidx.room.withTransaction
import good.bye.xml.local.room.FlickrDataBase
import good.bye.xml.local.room.dao.PhotoDao
import good.bye.xml.local.room.model.PhotoEntity
import good.bye.xml.local.room.model.PhotoRemoteKeyEntity
import javax.inject.Inject

class FlickrLocalDataSourceImpl @Inject constructor(
    private val photoDao: PhotoDao,
    private val database: FlickrDataBase
): FlickrLocalDataSource {

    override suspend fun getNextPage(): Int {
        return photoDao.getPagingKey().run { page.takeIf { canMorePaging } ?: 0 }
    }

    // 네이밍 변경 예정 clearToAddPhotos()
    override suspend fun clearToAddPhotos(photos: List<PhotoEntity>, nextPage: Int, totalPages: Int) {
        database.withTransaction {
            with(photoDao) {
                // Photo 테이블 clear
                deleteAll()

                // Photo 테이블 전체 추가
                insertAll(photos)

                // Photo Remote Key 테이블 추가
                replacePagingRemoteKey(PhotoRemoteKeyEntity(page = nextPage, totalPages = totalPages))
            }
        }
    }

    override suspend fun addPhotos(photos: List<PhotoEntity>, nextPage: Int, totalPages: Int) {
        with(photoDao) {
            insertAll(photos)
            replacePagingRemoteKey(PhotoRemoteKeyEntity(page = nextPage, totalPages = totalPages))
        }
    }

    override fun getPhotoPagingSource(): PagingSource<Int, PhotoEntity> {
        return photoDao.pagingSource()
    }
}