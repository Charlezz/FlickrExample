package kwon.dae.won.ui

import android.content.Context
import coil.request.ImageRequest
import coil.size.Size
import kwon.dae.won.data.BuildConfig

/**
 * @author Daewon on 10,February,2023
 */

/**
 * Default
 * memoryCachePolicy - ENABLED,
 * diskCachePolicy - ENABLED,
 * networkCachePolicy - ENABLED,
 */
fun loadImageData(context: Context, id: String, secret: String): ImageRequest = ImageRequest
    .Builder(context)
    .data(imageUrl(id, secret))
    .crossfade(true)
    .size(Size.ORIGINAL)
    .build()

fun imageUrl(id: String, secret: String): String = "https://live.staticflickr.com/${BuildConfig.SERVER_ID}/${id}_${secret}.jpg"

