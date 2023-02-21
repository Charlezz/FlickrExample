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
        return photoDao.getPagingKey().page
    }

    // 네이밍 변경 예정 clearToAddPhotos()
    override suspend fun clearToAddPhotos(photos: List<PhotoEntity>, nextPage: Int) {
        database.withTransaction {
            with(photoDao) {
                // Photo 테이블 clear
                deleteAll()

                // Photo Remote Key 테이블 clear
                clearPagingKey()

                // Photo 테이블 전체 추가
                insertAll(photos)

                // Photo Remote Key 테이블 추가
                replacePagingRemoteKey(PhotoRemoteKeyEntity(nextPage))
            }
        }
    }

    override suspend fun addPhotos(photos: List<PhotoEntity>, nextPage: Int) {
        with(photoDao) {
            insertAll(photos)
            clearPagingKey()
            replacePagingRemoteKey(PhotoRemoteKeyEntity(nextPage))
        }
    }

    override fun getPhotoPagingSource(): PagingSource<Int, PhotoEntity> {
        return photoDao.pagingSource()
    }
}