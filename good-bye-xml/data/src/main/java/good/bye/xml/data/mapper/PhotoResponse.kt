package good.bye.xml.data.mapper

import com.example.network.model.photos.PhotoResponse
import good.bye.xml.domain.model.photo.Photo
import good.bye.xml.local.room.model.PhotoEntity

fun PhotoResponse.toDomain(): Photo =
    Photo(
        id = id,
        secret = secret,
        server = server,
        title = title
    )

fun PhotoResponse.toLocal(): PhotoEntity =
    PhotoEntity(
        id = id,
        owner = owner,
        secret = secret,
        server = server,
        farm = farm,
        title = title,
        isPublic = isPublic,
        isFriend = isFriend,
        isFamily = isFamily
    )