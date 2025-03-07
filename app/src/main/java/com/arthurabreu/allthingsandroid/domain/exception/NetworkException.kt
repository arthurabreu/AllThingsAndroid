package com.arthurabreu.allthingsandroid.domain.exception

sealed class NetworkException(message: String) : Exception(message) {
    class ConnectionError(val code: String) : NetworkException("No internet connection: $code")
    class ServerError(val code: String) : NetworkException("Server error: $code")
    class TimeoutError(val code: String) : NetworkException("Request timeout: $code")
    class UnknownError(val code: String) : NetworkException("Unknown network error: $code")
    class SerializationError(val code: String) : NetworkException("Data serialization failed: $code")
}