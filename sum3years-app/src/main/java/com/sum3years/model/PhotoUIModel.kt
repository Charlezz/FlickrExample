package com.sum3years.model

import com.sum3years.data.model.Photo

data class PhotoUIModel(
    val id: String = "",
    val owner: String = "",
    val secret: String = "",
    val server: String = "",
    val title: String = "",
) {
    val loadUrlSmall: String // 검색화면 표시용
        get() = "https://live.staticflickr.com/$server/${id}_${secret}_n.jpg"
    val loadUrlMedium: String // 다운로드 확인용
        get() = "https://live.staticflickr.com/$server/${id}_$secret.jpg"
    val loadUrlOriginal: String // 다운로드용
        get() = "https://live.staticflickr.com/$server/${id}_${secret}_o.jpg"
    val photoId: String
        get() = "$owner/$id"
    val fileName: String
        get() = "${id}_$secret.jpg"

    var ratio: Float = 1f
}

fun Photo.toUIModel(): PhotoUIModel {
    return PhotoUIModel(
        id = id,
        owner = owner,
        secret = secret,
        server = server,
        title = title,
    )
}
