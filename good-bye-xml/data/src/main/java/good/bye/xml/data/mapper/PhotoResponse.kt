package good.bye.xml.data.mapper

import com.example.network.model.photos.PhotoResponse
import good.bye.xml.domain.model.photo.Photo

fun PhotoResponse.toDomain(): Photo =
    Photo(
        id = id,
        secret = secret,
        server = server,
        title = title
    )