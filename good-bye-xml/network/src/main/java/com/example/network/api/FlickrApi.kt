package com.example.network.api

import com.example.network.model.photos.GetPhotosResponse
import com.example.network.model.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

/** 쿼리스트링 - api_key, format, nojsoncallback 세가지는 인터셉터에 고정으로 추가
 *  @param api_key: 사용자 앱별로 발급받은 api_key
 *  @param format: 기본 응답형식은 REST 형식, 원하는 Json 형식으로 받기 위해 `format=json` 추가 (https://www.flickr.com/services/api/response.json.html)
 *  @param nojsoncallback: json format 형식으로 요청 시, 함수 래퍼로 감싸진 Json 형태로 반환, 함수 래퍼를 제외하기 위해 `nojsoncallback=1` 설정 (link 상동)
 *  @param method: 원하는 행위에 맞는 method (https://www.flickr.com/services/api/)
 *  */

interface FlickrApi {

    @GET("?method=flickr.photos.getRecent")
    suspend fun getPhotosForRecent(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): NetworkResult<GetPhotosResponse>

    @GET("?method=flickr.photos.search")
    suspend fun getPhotosForSearch(
        @Query("text") keyword: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): NetworkResult<GetPhotosResponse>
}