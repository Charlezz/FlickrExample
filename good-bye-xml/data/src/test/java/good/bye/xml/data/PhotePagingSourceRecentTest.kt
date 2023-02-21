package good.bye.xml.data.fake

import androidx.paging.PagingSource
import good.bye.xml.data.FakePhoto
import good.bye.xml.data.mapper.toDomain
import good.bye.xml.data.pagingsource.PhotoPagingSourceRecent
import good.bye.xml.data.remote.FlickrRemoteDataSource
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoPagingSourceRecentTest {

    private val fakeSuccessDataSourceRecent: FlickrRemoteDataSource =
        FakeSuccessFlickRemoteSourceImpl()
    private val fakeFailedDataSourceRecent: FlickrRemoteDataSource =
        FakeFailedFlickRemoteSourceImpl()

    @Test
    fun `페이징 정상적으로 반환되는가?`() = runTest {
        val pagingSource = PhotoPagingSourceRecent(fakeSuccessDataSourceRecent)

        pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 2,
                placeholdersEnabled = false
            )
        ) shouldBeEqualToComparingFields
            PagingSource.LoadResult.Page(
                data = listOf(FakePhoto.testPhoto1.toDomain(), FakePhoto.testPhoto2.toDomain()),
                prevKey = null,
                nextKey = FakePhoto.testPhoto2.id
            )
    }

    @Test
    fun `페이징 실패하였을때 정상적으로 처리되는가?`() = runTest {
        val pagingSource = PhotoPagingSourceRecent(fakeFailedDataSourceRecent)

        pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 2,
                placeholdersEnabled = false
            )
        ) shouldBe (
            PagingSource.LoadResult.Error(throwable = IllegalArgumentException())
            )
    }
}
