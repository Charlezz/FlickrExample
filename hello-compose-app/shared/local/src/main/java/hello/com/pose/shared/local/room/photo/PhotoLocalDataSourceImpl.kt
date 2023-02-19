package hello.com.pose.shared.local.room.photo

import androidx.paging.PagingSource
import hello.com.pose.shared.data.photo.PhotoData
import hello.com.pose.shared.data.photo.PhotoEntity
import hello.com.pose.shared.data.photo.PhotoLocalDataSource
import javax.inject.Inject

class PhotoLocalDataSourceImpl @Inject constructor(
    private val photoDao: PhotoDao,
) : PhotoLocalDataSource {
    override suspend fun getNextPage(): Int = photoDao.getRemoteKey().nextPage

    override suspend fun setPhotoList(photos: List<PhotoData>, nextPage: Int) {
        photoDao.replace(
            photos = photos.toEntities(),
            page = nextPage,
        )
    }

    override suspend fun addPhotoList(photos: List<PhotoData>, nextPage: Int) {
        photoDao.insertPhotoPage(
            photos = photos.toEntities(),
            nextPage = nextPage,
        )
    }

    override fun getPhotoPagingSource(): PagingSource<Int, PhotoEntity> {
        return photoDao.getPhotoPagingSource()
    }
}
