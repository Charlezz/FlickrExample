package good.bye.xml.data.fake

import androidx.paging.PagingSource
import com.example.network.model.NetworkResult
import com.example.network.model.photos.GetPhotosResponse
import com.example.network.model.photos.PhotosResponse
import good.bye.xml.data.FakePhoto
import good.bye.xml.data.mapper.toDomain
import good.bye.xml.data.pagingsource.PhotoPagingSourceRecent
import good.bye.xml.data.remote.FlickrRemoteDataSource
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoPagingSourceRecentTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val fakeSuccessDataSourceRecent: FlickrRemoteDataSource =
        FakeSuccessFlickRemoteSourceImpl()
    private val fakeFailedDataSourceRecent: FlickrRemoteDataSource =
        FakeFailedFlickRemoteSourceImpl()

    @MockK
    lateinit var mockRemoteDataSource: FlickrRemoteDataSource

    @Test
    fun `페이징 정상적으로 반환되는가? Fake용`() = runTest {
        val pagingSource = PhotoPagingSourceRecent(fakeSuccessDataSourceRecent)

        pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 2,
                placeholdersEnabled = false,
            ),
        ) shouldBeEqualToComparingFields
            PagingSource.LoadResult.Page(
                data = listOf(FakePhoto.testPhoto1.toDomain(), FakePhoto.testPhoto2.toDomain()),
                prevKey = null,
                nextKey = FakePhoto.testPhoto2.id,
            )
    }

    @Test
    fun `페이징 정상적으로 반환되는가? Mock용`() = runTest {
        val mockSuccess = NetworkResult.Success(
            GetPhotosResponse(
                photos = PhotosResponse(
                    page = 0,
                    pages = 20,
                    perpage = 20,
                    total = 5,
                    photo = listOf(
                        FakePhoto.testPhoto1,
                        FakePhoto.testPhoto2,

                    ),
                ),
                stat = "",
            ),
        )

        coEvery { mockRemoteDataSource.getPhotosForRecent(10, 10) } returns mockSuccess

        val pagingSource = PhotoPagingSourceRecent(mockRemoteDataSource)
        pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 2,
                placeholdersEnabled = false,
            ),
        ) shouldBeEqualToComparingFields
            PagingSource.LoadResult.Page(
                data = listOf(FakePhoto.testPhoto1.toDomain(), FakePhoto.testPhoto2.toDomain()),
                prevKey = null,
                nextKey = FakePhoto.testPhoto2.id,
            )

        coVerify { mockRemoteDataSource.getPhotosForRecent(10, 10) }
    }

    @Test
    fun `페이징 실패하였을때 정상적으로 처리되는가?`() = runTest {
        val pagingSource = PhotoPagingSourceRecent(fakeFailedDataSourceRecent)

        pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 2,
                placeholdersEnabled = false,
            ),
        ) shouldBe (
            PagingSource.LoadResult.Error(throwable = IllegalArgumentException())
            )
    }
}
