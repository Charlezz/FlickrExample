package good.bye.xml.local.room.datasource

import androidx.paging.PagingSource
import good.bye.xml.local.room.model.PhotoEntity

interface FlickrLocalDataSource {

    suspend fun getNextPage(): Int

    suspend fun clearToAddPhotos(photos: List<PhotoEntity>, nextPage: Int, totalPages: Int)

    suspend fun addPhotos(photos: List<PhotoEntity>, nextPage: Int, totalPages: Int)

    fun getPhotoPagingSource(): PagingSource<Int, PhotoEntity>
}