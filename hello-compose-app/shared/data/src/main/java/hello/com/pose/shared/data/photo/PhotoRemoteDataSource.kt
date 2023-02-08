package hello.com.pose.shared.data.photo

interface PhotoRemoteDataSource {
    suspend fun fetchFlickrSearchPhoto(query: String, page: Int): List<PhotoData>
}