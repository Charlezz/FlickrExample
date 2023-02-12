package kwon.dae.won.data.retrofit

import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import kwon.dae.won.data.model.JsonObjectKey
import okhttp3.ResponseBody
import okio.Okio
import retrofit2.Converter
import java.lang.reflect.*
import java.lang.reflect.Array

/**
 * @author soohwan.ok
 */
class FlickrMoshiConverter<T>(
    private val type: Type,
    private val moshi: Moshi
) : Converter<ResponseBody, FlickrResponse<T>> {

    private val rawType:Class<*> = getRawType(type)

    override fun convert(value: ResponseBody): FlickrResponse<T> {
        val jsonReader: JsonReader = JsonReader.of(Okio.buffer(Okio.source(value.byteStream())))
        val jsonValue: Map<*, *>? = jsonReader.readJsonValue() as? Map<*, *>

        val stat: String? = jsonValue?.get("stat") as String?
        val code: Double? = jsonValue?.get("code") as Double?
        val message: String? = jsonValue?.get("message") as String?

        return if (stat == "ok") {
            val jsonObjectKey:JsonObjectKey? = rawType.getAnnotation(JsonObjectKey::class.java)
            val newValue = jsonObjectKey?.value?.let{ key->
                val innerJsonValue:Map<*, *>? = jsonValue?.get(key) as? Map<*, *>
                moshi.adapter<T>(type).fromJsonValue(innerJsonValue)
            }?:moshi.adapter<T>(type).fromJsonValue(jsonValue)
            FlickrResponse.Success(value = newValue)
        } else {
            FlickrResponse.Failure(code = code?.toInt(), message = message)
        }
    }

    private fun getRawType(type: Type): Class<*> {
        if (type is Class<*>) {
            // 일반 클래스일 경우 그대로 반환
            return type
        }
        if (type is ParameterizedType) {

            // Type.getRawType()이 Class를 반환하지 않고 왜 Type을 반환 하는지 모르겠지만,
            // Nested Class와 관련해서 뭔가 엣지 케이스가 있는 듯하다.
            val rawType = type.rawType
            if (rawType !is Class<*>) {
                throw IllegalArgumentException()
            }
            return rawType
        }
        if (type is GenericArrayType) {
            val componentType = type.genericComponentType
            return Array.newInstance(getRawType(componentType), 0).javaClass
        }
        if (type is TypeVariable<*>) {
            return Any::class.java
        }
        if (type is WildcardType) {
            return getRawType(type.upperBounds[0])
        }
        throw IllegalArgumentException(
            "Class, ParameterizedType 또는 GenericArrayType 를 반환해야 합니다. <$type> 는 ${type.javaClass.name}의 타입 입니다."
        )
    }
}