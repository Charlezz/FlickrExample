package kwon.dae.won.domain.repository

import kwon.dae.won.domain.model.Photo

/**
 * @author soohwan.ok
 */
interface FlickrRepository {

    suspend fun getRecent(perPage: Int, page: Int): Result<List<Photo>>

    suspend fun search(text: String, perPage: Int, page: Int): Result<List<Photo>>
}