package kwon.dae.won.data.retrofit

import com.squareup.moshi.Moshi
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type

/**
 * @author soohwan.ok
 */
class FlickrMoshiConverterFactory private constructor(
    private val moshi: Moshi,
) : Converter.Factory() {

    private val originalMoshiConverterFactory = MoshiConverterFactory.create(moshi)

    companion object {
        fun create(moshi: Moshi = Moshi.Builder().build()): FlickrMoshiConverterFactory {
            return FlickrMoshiConverterFactory(
                moshi = moshi
            )
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return FlickrMoshiConverter<Any>(type, moshi)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return originalMoshiConverterFactory.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }

    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        return originalMoshiConverterFactory.stringConverter(type, annotations, retrofit)
    }

}