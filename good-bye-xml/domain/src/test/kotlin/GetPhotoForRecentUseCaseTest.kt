package good.bye.xml.data.fake

import androidx.paging.PagingData
import good.bye.xml.data.fake.dto.FakePhoto
import good.bye.xml.domain.model.photo.Photo
import good.bye.xml.domain.repository.FlickrRepository
import good.bye.xml.domain.usecase.GetPhotoForRecentUseCase
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPhotoForRecentUseCaseTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var mockRepository: FlickrRepository

    @Test
    fun `페이징 데이터가 성공일때 USE CASE는 PagingData를 처리하는가?`() = runTest {
        val pagingData = PagingData.from(data = listOf(FakePhoto.FakePhoto1, FakePhoto.FakePhoto2))
        coEvery { mockRepository.getPhotosForRecent(10, 10) }.returns(
            flow<PagingData<Photo>> { emit(pagingData) }
        )

        val getPhotoForRecentUseCase = GetPhotoForRecentUseCase(mockRepository)

        getPhotoForRecentUseCase(10, 10).collect {
            it.shouldBeEqualToComparingFields(pagingData)
        }
    }
}
