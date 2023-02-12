package hello.com.pose.shared.data.photo

import hello.com.pose.shared.domain.photo.Photo

internal fun PhotoData.toDomain(): Photo = Photo(id = id, secret = secret, server = server)