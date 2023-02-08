package kwon.dae.won.domain.usecase

import kwon.dae.won.domain.model.Photo
import kwon.dae.won.domain.repository.FlickrRepository
import javax.inject.Inject

/**
 * @author soohwan.ok
 */
class GetRecentUseCase @Inject constructor(
    private val repository: FlickrRepository
) {
    suspend operator fun invoke(perPage: Int = 100, page: Int = 1): Result<List<Photo>> {
        return repository.getRecent(perPage = perPage, page = page)
    }
}