/*
 * Designed and developed by 2022 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.network

import com.example.network.api.FlickrApi
import com.example.network.model.NetworkResult
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldBeTypeOf
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
        enqueueResponse("/LoginResponse.json")
        val response = service.getPhotosForRecent(10, 20)
        val responseBody = requireNotNull((response))


        response.shouldBeTypeOf()
//        assert(response is NetworkResult.Success)
    }
}
