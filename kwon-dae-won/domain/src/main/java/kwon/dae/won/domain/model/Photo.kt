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
    val page: Int,
    val maxPage: Int,
) {
    public companion object
}

fun Photo.Companion.fake(): List<Photo> {
    val fakeList = mutableListOf<Photo>()
    repeat(10) {
        fakeList.addAll(
            listOf(
                Photo(
                    id = "52699173437",
                    owner = "194801016@N07",
                    secret = "26b04f2a1f",
                    title = "20230217_13041500-Edit",
                    page = 1,
                    maxPage = 30
                ),
                Photo(
                    id = "52699174817",
                    owner = "146092138@N05",
                    secret = "7c85a12b86",
                    title = "Museo de las ciencias (Valencia)",
                    page = 1,
                    maxPage = 30
                ),
                Photo(
                    id = "52699177047",
                    owner = "42113467@N07",
                    secret = "08ea0d2b13",
                    title = "Photowalk 2023-02-23",
                    page = 1,
                    maxPage = 30
                ),
                Photo(
                    id = "52699178897",
                    owner = "59216200@N08",
                    secret = "c295b12039",
                    title = "Dharma city",
                    page = 1,
                    maxPage = 30
                )
            )
        )
    }
    return fakeList
}
