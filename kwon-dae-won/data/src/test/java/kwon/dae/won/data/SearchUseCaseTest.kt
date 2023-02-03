package kwon.dae.won.data

import kotlinx.coroutines.runBlocking
import kwon.dae.won.data.repository.FlickrRepositoryImpl
import kwon.dae.won.data.retrofit.FlickrRetrofitFactory
import kwon.dae.won.data.retrofit.FlickrService
import kwon.dae.won.domain.repository.FlickrRepository
import kwon.dae.won.domain.usecase.SearchUseCase
import org.junit.Before
import org.junit.Test

/**
 * @author soohwan.ok
 * @since
 */
class SearchUseCaseTest {

    private lateinit var service: FlickrService
    private lateinit var repository: FlickrRepository
    private lateinit var searchUseCase: SearchUseCase

    @Before
    fun setup() {
        service = FlickrRetrofitFactory.create().create(FlickrService::class.java)
        repository = FlickrRepositoryImpl(service)
        searchUseCase = SearchUseCase(repository)
    }

    @Test
    fun searchUseCaseTest() = runBlocking {
        val result = searchUseCase("tree")
        if (result.isFailure) {
            try {
                println(result.getOrThrow())
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        } else if (result.isSuccess) {
            println(result.getOrThrow())
        } else {
            println("??")
        }
    }
}