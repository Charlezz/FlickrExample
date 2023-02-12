package good.bye.xml.domain.repository

import good.bye.xml.domain.model.photo.Photo
import good.bye.xml.domain.model.ResultWrapper

interface FlickrRepository {

    suspend fun getPhotosForRecent(perPage: Int, page: Int): ResultWrapper<List<Photo>>

    suspend fun getPhotosForSearch(keyword: String, perPage: Int, page: Int): ResultWrapper<List<Photo>>
}