package hello.com.pose.shared.domain.photo

interface PhotoRepository {
    suspend fun fetchSearchPhoto(query: String, page: Int): List<Photo>
}