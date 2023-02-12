package com.example.network.model


sealed class NetworkResult<out T> {

    // 서버로부터 성공적으로 2xx 응답을 받았을 때
    data class Success<out T>(val data: T): NetworkResult<T>()

    // 서버로 부터 오류 코드와 메시지가 포함된 응답을 받았을 때
    data class Error(val code: Int, val message: String?): NetworkResult<Nothing>()

    // IOException, UnKnowHostException과 같은 예외로 서버로 부터 응답을 받지 못한 경우
    data class Exception(val exception: Throwable): NetworkResult<Nothing>()
}
