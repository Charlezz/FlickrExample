package good.bye.xml.domain.usecase

import androidx.paging.PagingData
import good.bye.xml.domain.model.photo.Photo
import good.bye.xml.domain.model.ResultWrapper
import good.bye.xml.domain.repository.FlickrRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotoForRecentUseCase @Inject constructor(
    private val repository: FlickrRepository
) {
    suspend operator fun invoke(perPage: Int = 50, page: Int = 1): Flow<PagingData<Photo>> {
        return repository.getPhotosForRecent(perPage, page)
    }
}