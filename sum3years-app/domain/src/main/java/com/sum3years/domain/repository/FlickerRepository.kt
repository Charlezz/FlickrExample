package com.sum3years.domain.repository

import com.sum3years.data.model.Photo


interface FlickerRepository {
    suspend fun getPhotos(query: String, page: Int, pageSize: Int): Result<List<Photo>>
}
