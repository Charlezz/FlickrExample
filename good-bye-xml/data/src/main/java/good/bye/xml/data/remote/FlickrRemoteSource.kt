package good.bye.xml.data.remote

import com.ddd.pollpoll.core.network.handle.executeHandle
import com.ddd.pollpoll.core.network.model.LoginRequest
import com.ddd.pollpoll.core.network.model.LoginResponse
import com.ddd.pollpoll.core.network.retrofit.PollAPI
import javax.inject.Inject

interface FlickrRemoteSource {

    suspend fun loginGoogle(token: LoginRequest): LoginResponse
}

class FlickrRemoteSourceImp @Inject constructor(
    private val pollAPI: PollAPI
) : FlickrRemoteSource {
    override suspend fun loginGoogle(token: LoginRequest): LoginResponse = pollAPI.loginGoogle(token).executeHandle()
}
