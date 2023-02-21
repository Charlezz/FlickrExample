package com.example.network

import com.example.network.api.FlickrApi
import com.example.network.model.NetworkResult
import com.example.network.model.photos.GetPhotosResponse
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class FlickApiTest : ApiAbstract<FlickrApi>() {

    private lateinit var service: FlickrApi

    @Before
    fun initService() {
        service = createService(FlickrApi::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun `페이징 데이터가 제대로 파싱 되는가?`() = runTest {
        enqueueResponse("/paging-response.json")
        val response = service.getPhotosForSearch("", 10, 20)
        val responseBody = requireNotNull((response))

        response.shouldBeInstanceOf<NetworkResult.Success<GetPhotosResponse>>()
    }

    @Throws(IOException::class)
    @Test
    fun `500 나올땐 정상적으로 Error 처리가 나오는가?`() = runTest {
        enqueueError()
        val response = service.getPhotosForSearch("", 10, 20)

        response.shouldBeInstanceOf<NetworkResult.Error>()
    }
}
