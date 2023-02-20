package kwon.dae.won.data

import androidx.paging.PagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kwon.dae.won.data.repository.FakeFlickrRepositoryImpl
import kwon.dae.won.data.repository.FlickrRepositoryImpl
import kwon.dae.won.data.retrofit.FlickrRetrofitFactory
import kwon.dae.won.data.retrofit.FlickrService
import kwon.dae.won.data.room.KeywordPagingSource
import kwon.dae.won.domain.model.Photo
import kwon.dae.won.domain.model.fake
import kwon.dae.won.domain.repository.FlickrRepository
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

/**
 * @author Daewon on 21,February,2023
 *
 */
class KeywordPagingSourceTest {

    private lateinit var service: FlickrService
    private lateinit var repository: FlickrRepository

    @Before
    fun setup() {
        repository = FakeFlickrRepositoryImpl()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runTest {
        val pagingSource = KeywordPagingSource(repository, "test")
        assertEquals(
            PagingSource.LoadResult.Page(
                data = Photo.fake(),
                prevKey = null,
                nextKey = Photo.fake().first().page.plus(1)
            ),
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            ),
        )
    }


}
