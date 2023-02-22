package hello.com.pose.shared.domain.photo

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotoPagingSourceUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Photo>> {
        return photoRepository.getPhotoPagingSourceFlow(query)
    }
}
