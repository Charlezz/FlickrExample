package good.bye.xml.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.network.model.NetworkResult
import com.example.network.model.photos.PhotoResponse
import good.bye.xml.data.mapper.toDomain
import good.bye.xml.data.remote.FlickrRemoteDataSource
import good.bye.xml.domain.model.photo.Photo
import java.io.IOException

class PhotoPagingSourceSearch constructor(
    private val dataSource: FlickrRemoteDataSource,
    private val keyword: String
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val page = params.key ?: 1
            val response =
                if (keyword.isNotBlank()) dataSource.getPhotosForSearch(
                    keyword = keyword,
                    perPage = params.loadSize,
                    page = page
                ) else dataSource.getPhotosForRecent(perPage = params.loadSize, page = page)

            when (response) {
                is NetworkResult.Success ->
                    LoadResult.Page(
                        data = response.data.photos.photo.map(PhotoResponse::toDomain),
                        prevKey = if (page == 1) null else page.minus(1),
                        nextKey = if (response.data.photos.photo.isEmpty()) null else page.plus(1)
                    )

                is NetworkResult.Error ->
                    LoadResult.Error(IllegalArgumentException("code: ${response.code}, message: ${response.message}"))

                is NetworkResult.Exception ->
                    LoadResult.Error(response.exception)
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }
}
