package kwon.dae.won.data.repository

import kwon.dae.won.data.mapper.PhotoMapper
import kwon.dae.won.data.retrofit.FlickrResponse
import kwon.dae.won.data.retrofit.FlickrService
import kwon.dae.won.domain.model.Photo
import kwon.dae.won.domain.repository.FlickrRepository
import javax.inject.Inject

/**
 * @author soohwan.ok
 */
class FlickrRepositoryImpl @Inject constructor(
    private val service: FlickrService
) : FlickrRepository {
    override suspend fun getRecent(perPage: Int, page: Int): Result<List<Photo>> {
        if (page <= 0 || perPage !in 1..500) {
            return Result.failure(IllegalArgumentException("요청이 올바르지 않습니다. perPage = $perPage, page = $page"))
        }
        return when (val response = service.getRecent(perPage, page)) {
            is FlickrResponse.Success -> Result.success(response.value?.photo?.map { dto ->
                PhotoMapper.from(
                    dto.copy(page = response.value.page, maxPage = response.value.pages)
                )
            } ?: emptyList())
            is FlickrResponse.Failure -> Result.failure(IllegalStateException("code = ${response.code}, error = ${response.message}"))
            is FlickrResponse.NetworkError -> Result.failure(response.exception)
            is FlickrResponse.Unexpected -> Result.failure(response.t ?: UnknownError())
        }

    }

    override suspend fun search(text: String, perPage: Int, page: Int): Result<List<Photo>> {
        if (page <= 0 || perPage !in 1..500 || text.isBlank()) {
            return Result.failure(IllegalArgumentException("요청이 올바르지 않습니다. text = $text, perPage = $perPage, page = $page"))
        }
        return when (val response = service.search(text, perPage, page)) {
            is FlickrResponse.Success -> Result.success(response.value?.photo?.map { dto ->
                PhotoMapper.from(
                    dto
                )
            } ?: emptyList())
            is FlickrResponse.Failure -> Result.failure(IllegalStateException("code = ${response.code}, error = ${response.message}"))
            is FlickrResponse.NetworkError -> Result.failure(response.exception)
            is FlickrResponse.Unexpected -> Result.failure(response.t ?: UnknownError())
        }
    }

}
