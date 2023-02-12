package kwon.dae.won.data

import kotlinx.coroutines.runBlocking
import kwon.dae.won.data.repository.FlickrRepositoryImpl
import kwon.dae.won.data.retrofit.FlickrService
import kwon.dae.won.data.retrofit.FlickrRetrofitFactory
import kwon.dae.won.domain.model.Photo
import kwon.dae.won.domain.repository.FlickrRepository
import kwon.dae.won.domain.usecase.GetRecentUseCase
import kwon.dae.won.domain.usecase.SearchUseCase
import org.junit.Before
import org.junit.Test

/**
 * @author soohwan.ok
 * 그냥 콘솔에 로그 잘 찍히는지 확인하는 용도
 */
class FlickrRepositoryTest {

    private lateinit var service: FlickrService
    private lateinit var repository: FlickrRepository

    @Before
    fun setup() {
        service = FlickrRetrofitFactory.create().create(FlickrService::class.java)
        repository = FlickrRepositoryImpl(service)
    }

    @Test
    fun getRecentTest() = runBlocking {
        assertResult(repository.getRecent(100, 1))
    }

    @Test
    fun searchUseCaseTest() = runBlocking {
        assertResult(repository.search("text", 100, 1))
    }

    private fun assertResult(result: Result<List<Photo>>) {
        if (result.isFailure) {
            try {
                println(result.getOrThrow())
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        } else {
            println(result.getOrThrow())
        }
    }
}