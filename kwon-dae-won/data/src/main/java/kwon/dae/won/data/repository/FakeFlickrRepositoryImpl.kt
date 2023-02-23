package kwon.dae.won.data.repository

import kwon.dae.won.domain.model.Photo
import kwon.dae.won.domain.model.fake
import kwon.dae.won.domain.repository.FlickrRepository

/**
 * @author Daewon on 21,February,2023
 *
 */
class FakeFlickrRepositoryImpl : FlickrRepository {


    override suspend fun getRecent(perPage: Int, page: Int): Result<List<Photo>> {
        return Result.success(Photo.fake())
    }

    override suspend fun search(text: String, perPage: Int, page: Int): Result<List<Photo>> {
        return Result.success(Photo.fake())
    }
}
