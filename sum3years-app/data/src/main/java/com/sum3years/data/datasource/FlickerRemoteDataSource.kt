package com.sum3years.data.datasource

import android.util.Log
import com.sum3years.data.BuildConfig
import com.sum3years.data.model.FlickerException
import com.sum3years.data.model.Photo
import com.sum3years.data.network.FlickerService
import java.net.UnknownHostException
import javax.inject.Inject

class FlickerRemoteDataSource @Inject constructor(
    private val service: FlickerService,
) : FlickerDataSource {
    override suspend fun getPhotos(query: String, page: Int, pageSize: Int): Result<List<Photo>> {
        return runCatching {
            service.getPhotos(
                apiKey = BuildConfig.FLICKER_API_KEY,
                text = query,
                page = page,
                pageSize = pageSize,
            )
        }.fold(
            onSuccess = {
                Log.d("로그", "FlickerRemoteDataSource_getPhotos: success! $it")
                if (it.body()?.code == 200) {
                    Result.success(it.body()?.photos?.photo ?: emptyList())
                } else {
                    Result.failure(FlickerException.fromCode(it.body()?.code ?: -1))
                }
            },
            onFailure = { exception ->
                Log.d("로그", "FlickerRemoteDataSource_getPhotos: exception accured! $exception ")
                val flickerException = when (exception) {
                    is FlickerException -> exception
                    is UnknownHostException -> FlickerException.Exception("인터넷 연결을 확인하세요")
                    else -> FlickerException.UnknownError
                }
                Result.failure(flickerException)
            },
        )
    }
}
