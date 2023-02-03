package kwon.dae.won.data.retrofit

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * @author soohwan.ok
 */
class FlickrCallAdapter<R:FlickrResponse<*>>(private val responseType: Type):CallAdapter<R, FlickrCall<R>> {
    override fun responseType(): Type  = responseType

    override fun adapt(call: Call<R>): FlickrCall<R> {
        return FlickrCall(call)
    }
}