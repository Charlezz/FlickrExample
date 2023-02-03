package kwon.dae.won.domain.usecase

import kwon.dae.won.domain.model.Photo
import kwon.dae.won.domain.repository.FlickrRepository
import javax.inject.Inject

/**
 * @author soohwan.ok
 */
class SearchUseCase @Inject constructor(
    private val repository: FlickrRepository
) {
    suspend operator fun invoke(
        text: String,
        perPage: Int = 100,
        page: Int = 1
    ): Result<List<Photo>> {
        return repository.search(text, perPage, page)
    }
}