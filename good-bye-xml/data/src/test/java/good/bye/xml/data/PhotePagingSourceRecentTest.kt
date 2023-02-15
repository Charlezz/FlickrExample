package good.bye.xml.data.fake

import androidx.paging.PagingSource
import good.bye.xml.data.FakePhoto
import good.bye.xml.data.mapper.toDomain
import good.bye.xml.data.pagingsource.PhotoPagingSourceRecent
import good.bye.xml.data.remote.FlickrRemoteDataSource
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoPagingSourceRecentTest {

    private val fakeDataSourceRecent: FlickrRemoteDataSource = FakeFlickRemoteSourceImpl()

    @Test
    fun loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runTest {
        val pagingSource = PhotoPagingSourceRecent(fakeDataSourceRecent)
        pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 2,
                placeholdersEnabled = false
            )
        ).shouldBe(
            PagingSource.LoadResult.Page(
                data = listOf(FakePhoto.testPhoto1.toDomain(), FakePhoto.testPhoto2.toDomain()),
                prevKey = null,
                nextKey = FakePhoto.testPhoto2.id
            )
        )
    }
}
