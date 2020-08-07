package com.vishesh.gallery.utils

import com.vishesh.gallery.domain.entities.Photo
import okhttp3.internal.http2.ErrorCode
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class ResponseHandler {
    fun handleSuccess(data: List<Photo>): Resource.Success {
        return Resource.Success(data)
    }

    fun handleException(e: Exception): Resource.Error {
        return when (e) {
            is HttpException -> Resource.Error(e.code(), getErrorMessage(e.code()))
            is SocketTimeoutException -> Resource.Error(
                ErrorCode.CONNECT_ERROR.httpCode,
                getErrorMessage(ErrorCode.CONNECT_ERROR.httpCode)
            )
            is UnknownHostException -> Resource.Error(
                ErrorCode.INTERNAL_ERROR.httpCode,
                "Internet is not available. Kindly Retry after connecting to the internet"
            )
            else -> Resource.Error(Int.MIN_VALUE, "Something went wrong")
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCode.CONNECT_ERROR.httpCode -> "Timeout, Check Internet Connection"
            401 -> "Check authorization. Try again!"
            403 -> "You are not authorized for this action"
            404 -> "404 Not Found"
            500 -> "Bad Request. Try Again!"
            else -> "Internal Server Error."
        }
    }
}