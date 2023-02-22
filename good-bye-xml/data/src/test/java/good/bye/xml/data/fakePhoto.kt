package good.bye.xml.data

import com.example.network.model.photos.PhotoResponse

object FakePhoto {
    val testPhoto1 = PhotoResponse(
        id = 1,
        owner = "",
        secret = "",
        server = 0,
        farm = 0,
        title = "테스트1",
        isPublic = 0,
        isFriend = 0,
        isFamily = 0
    )

    val testPhoto2 = PhotoResponse(
        id = 2,
        owner = "",
        secret = "",
        server = 0,
        farm = 0,
        title = "테스트2",
        isPublic = 0,
        isFriend = 0,
        isFamily = 0
    )
}
