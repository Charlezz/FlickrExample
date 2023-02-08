package hello.com.pose.shared.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlickrResponse(
    @Json(name = "photos")
    val photos: Photos? = Photos(),
    @Json(name = "stat")
    val stat: String? = ""
) {
    @JsonClass(generateAdapter = true)
    data class Photos(
        @Json(name = "page")
        val page: Int? = 0,
        @Json(name = "pages")
        val pages: Int? = 0,
        @Json(name = "perpage")
        val perpage: Int? = 0,
        @Json(name = "photo")
        val photo: List<Photo?>? = listOf(),
        @Json(name = "total")
        val total: Int? = 0
    ) {
        @JsonClass(generateAdapter = true)
        data class Photo(
            @Json(name = "farm")
            val farm: Int? = 0,
            @Json(name = "id")
            val id: String? = "",
            @Json(name = "isfamily")
            val isfamily: Int? = 0,
            @Json(name = "isfriend")
            val isfriend: Int? = 0,
            @Json(name = "ispublic")
            val ispublic: Int? = 0,
            @Json(name = "owner")
            val owner: String? = "",
            @Json(name = "secret")
            val secret: String? = "",
            @Json(name = "server")
            val server: String? = "",
            @Json(name = "title")
            val title: String? = ""
        )
    }
}