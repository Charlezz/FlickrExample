

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
}
