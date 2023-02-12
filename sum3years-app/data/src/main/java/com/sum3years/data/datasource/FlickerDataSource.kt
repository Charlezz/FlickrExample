package com.sum3years.data.datasource

import com.sum3years.data.model.Photo


interface FlickerDataSource {
    suspend fun getPhotos(query: String, page: Int, pageSize: Int): Result<List<Photo>>
}
