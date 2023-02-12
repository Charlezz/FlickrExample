package good.bye.xml.domain.repository

import androidx.paging.PagingData
import good.bye.xml.domain.model.photo.Photo
import kotlinx.coroutines.flow.Flow

interface FlickrRepository {

    suspend fun getPhotosForRecent(perPage: Int, page: Int): Flow<PagingData<Photo>>
    suspend fun getPhotosForSearch(keyword: String, perPage: Int, page: Int): Flow<PagingData<Photo>>
}