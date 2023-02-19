package hello.com.pose.shared.data.photo

import androidx.paging.PagingSource

interface PhotoLocalDataSource {

    suspend fun getNextPage(): Int

    suspend fun setPhotoList(photos: List<PhotoData>, nextPage: Int)

    suspend fun addPhotoList(photos: List<PhotoData>, nextPage: Int)

    fun getPhotoPagingSource(): PagingSource<Int, PhotoEntity>
}
