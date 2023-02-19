package hello.com.pose.shared.domain.photo

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    suspend fun fetchSearchPhoto(query: String, page: Int): List<Photo>

    fun getPhotoPagingSourceFlow(query: String): Flow<PagingData<Photo>>
}
