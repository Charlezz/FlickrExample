package good.bye.xml.data.mapper

import good.bye.xml.domain.model.photo.Photo
import good.bye.xml.local.room.model.PhotoEntity

fun PhotoEntity.toDomain(): Photo =
    Photo(
        id = id,
        secret = secret,
        server = server,
        title = title,
    )