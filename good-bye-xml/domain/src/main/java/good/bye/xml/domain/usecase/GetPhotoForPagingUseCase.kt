package good.bye.xml.domain.usecase

import androidx.paging.PagingData
import good.bye.xml.domain.model.photo.Photo
import good.bye.xml.domain.model.ResultWrapper
import good.bye.xml.domain.repository.FlickrRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotoForPagingUseCase @Inject constructor(
    private val repository: FlickrRepository
) {
    suspend operator fun invoke(keyword: String?, perPage: Int = 50): Flow<PagingData<Photo>> {
        return repository.loadPhotosForPaging(keyword = keyword, perPage = perPage)
    }
}