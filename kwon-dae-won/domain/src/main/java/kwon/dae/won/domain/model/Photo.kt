package kwon.dae.won.domain.model

/**
 * @author soohwan.ok
 * @see <a href="https://www.flickr.com/services/api/misc.urls.html">이미지 불러 오는 방법</a>
 */
data class Photo(
    val id: String,
    val owner: String,
    val secret: String,
    val title: String,
)
