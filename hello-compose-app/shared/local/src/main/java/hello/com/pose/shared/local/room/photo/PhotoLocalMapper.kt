package hello.com.pose.shared.local.room.photo

import hello.com.pose.shared.data.photo.PhotoData
import hello.com.pose.shared.data.photo.PhotoEntity

fun PhotoData.toEntity() = PhotoEntity(
    id = id,
    server = server,
    secret = secret,
    title= title
)

fun List<PhotoData>.toEntities() = map { it.toEntity() }