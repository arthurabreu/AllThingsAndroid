package com.arthurabreu.allthingsandroid.data.error

import com.arthurabreu.allthingsandroid.domain.exceptions.DomainException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

interface ErrorHandler {
    fun handleError(exception: Exception): DomainException
}

class ErrorHandlerImpl : ErrorHandler {
    override fun handleError(exception: Exception): DomainException = when (exception) {
        is SocketTimeoutException -> DomainException.TimeoutError(
            message = exception.message ?: "Request timed out"
        )
        is ClientRequestException -> DomainException.ServerError(
            code = exception.response.status.value,
            message = exception.message
        )
        is ServerResponseException -> DomainException.ServerError(
            code = exception.response.status.value,
            message = exception.message
        )
        is IOException -> DomainException.NetworkError(
            message = exception.message ?: "Network connection failed"
        )
        is SerializationException -> DomainException.SerializationError(
            message = exception.message ?: "Serialization error"
        )
        else -> DomainException.UnknownError(
            message = exception.message ?: "Unknown error occurred"
        )
    }
}

