package com.arthurabreu.allthingsandroid.domain.exceptions

sealed class DomainException(message: String) : Exception(message) {
    data class NetworkError(override val message: String) : DomainException(message)
    data class ServerError(val code: Int, override val message: String) : DomainException("$code: $message")
    data class TimeoutError(override val message: String) : DomainException(message)
    data class SerializationError(override val message: String) : DomainException(message)
    data class UnknownError(override val message: String) : DomainException(message)
}