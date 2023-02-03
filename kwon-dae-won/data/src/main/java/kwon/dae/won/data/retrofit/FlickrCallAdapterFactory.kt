package kwon.dae.won.data.retrofit

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author soohwan.ok
 */
class FlickrCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        // 반환 타입이 Call 이면 따로 처리 안하므로, 그냥 null 반환
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        // ParameterizedType 인지 먼저 확인한다.
        check(returnType is ParameterizedType) {
            "반환 타입은 반드시 ${FlickrCall::class.java.name}<Foo> 또는 ${FlickrCall::class.java.name}<out Foo>과 같은 형식 이어야 합니다."
        }

        /**
         * [FlickrCall] 타입에서 제네릭에 포함 된 응답 타입을 가져온다
         */
        val responseType = getParameterUpperBound(0, returnType)
        // if the response type is not ApiResponse then we can't handle this type, so we return null
        if (getRawType(responseType) != FlickrResponse::class.java) {
            return null
        }
        /**
         * 응답 타입이 [FlickrResponse] 이면 반드시 제네릭을 갖고 있어야 한다.
         */
        check(responseType is ParameterizedType) { "Response must be parameterized as NetworkResponse<Foo> or NetworkResponse<out Foo>" }

        val successBodyType = getParameterUpperBound(0, responseType)

        return FlickrCallAdapter<FlickrResponse<*>>(successBodyType)
    }

}