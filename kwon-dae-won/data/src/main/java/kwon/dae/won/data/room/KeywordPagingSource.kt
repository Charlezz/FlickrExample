package kwon.dae.won.data.room

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kwon.dae.won.domain.model.Photo
import kwon.dae.won.domain.repository.FlickrRepository
import retrofit2.HttpException
import java.io.IOException
import java.lang.Integer.max
import javax.inject.Inject

/**
 * @author Daewon on 20,February,2023
 *
 */
class KeywordPagingSource @Inject constructor(
    private val flickrRepository: FlickrRepository,
    private val keyword: String,
) : PagingSource<Int, Photo>() {

    private fun ensureValidKey(key: Int) = max(1, key)

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val photo = state.closestItemToPosition(anchorPosition) ?: return null
        return photo.page
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        try {
            val nextPage = params.key ?: 1
            val apiResponse = flickrRepository.search(text = keyword, perPage = 30, page = nextPage)
            apiResponse.onSuccess {
                return LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey =  if(it.lastOrNull()?.page == it.lastOrNull()?.maxPage) null else it.lastOrNull()?.page?.plus(1)
                )
            }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
        return LoadResult.Invalid()
    }
}
