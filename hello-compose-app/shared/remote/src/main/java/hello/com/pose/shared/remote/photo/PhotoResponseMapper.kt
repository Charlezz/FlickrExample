package hello.com.pose.shared.remote.photo

import hello.com.pose.shared.data.photo.PhotoData
import hello.com.pose.shared.remote.response.FlickrResponse

internal fun FlickrResponse.Photos.Photo.toData() = PhotoData(
    id = id.orEmpty(),
    secret = secret.orEmpty(),
    server = server.orEmpty()
)