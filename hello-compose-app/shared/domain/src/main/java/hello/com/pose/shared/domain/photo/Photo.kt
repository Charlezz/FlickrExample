package hello.com.pose.shared.domain.photo

data class Photo(
    val id: String,
    val secret: String,
    val server: String,
) {
    fun getStaticImageUrl(): String {
        return "https://live.staticflickr.com/${server}/${id}_${secret}.jpg"
    }
}