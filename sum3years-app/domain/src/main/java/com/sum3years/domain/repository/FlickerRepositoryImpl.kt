package com.sum3years.domain.repository

import com.sum3years.data.datasource.FlickerDataSource
import com.sum3years.data.model.Photo
import javax.inject.Inject

class FlickerRepositoryImpl @Inject constructor(
    private val dataSource: FlickerDataSource
    ) : FlickerRepository {
    override suspend fun getPhotos(query: String, page: Int, pageSize: Int): Result<List<Photo>> {
        return dataSource.getPhotos(query, page, pageSize)
    }
}
