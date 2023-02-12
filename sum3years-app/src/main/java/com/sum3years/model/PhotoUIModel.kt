package com.sum3years.model

import com.sum3years.data.model.Photo

data class PhotoUIModel(
    val id: String = "",
    val owner: String = "",
    val secret: String = "",
    val server: String = "",
    val title: String = "",
) {
    val loadUrl: String
        get() = "https://live.staticflickr.com/$server/${id}_$secret.jpg"
    val loadUrlSmall: String
        get() = "https://live.staticflickr.com/$server/${id}_${secret}_n.jpg"
    val loadUrlOriginal: String
        get() = "https://live.staticflickr.com/$server/${id}_${secret}_o.jpg"
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
