package com.sum3years.data.datasource

import com.sum3years.data.BuildConfig
import com.sum3years.data.model.FlickerException
import com.sum3years.data.model.Photo
import com.sum3years.data.network.FlickerService

class FlickerRemoteDataSource(private val service: FlickerService) : FlickerDataSource {
    override suspend fun getPhotos(query: String, page: Int, pageSize: Int): Result<List<Photo>> {
        return service.getPhotos(
            apiKey = BuildConfig.FLICKER_API_KEY,
            text = query,
            page = page,
            pageSize = pageSize,
        ).runCatching {
            if (code() == 200) {
                body()?.photos?.photo ?: emptyList()
            } else {
                throw (FlickerException.fromCode(code()))
            }
        }
    }
}
