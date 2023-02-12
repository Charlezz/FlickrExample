package good.bye.xml.domain.model.photo

data class Photo(
    val id: Long,
    val secret: String,
    val server: Int,
    val title: String,
) {
    val formattedUrl: String = "https://live.staticflickr.com/${server}/${id}_${secret}.jpg"
}
