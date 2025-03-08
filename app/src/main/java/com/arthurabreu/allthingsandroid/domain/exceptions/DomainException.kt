package com.arthurabreu.allthingsandroid.domain.exceptions

/*
    * DomainException is a sealed class that represents the different types of errors that can occur
    * in the domain layer of the application.
    * It has five subclasses that represent the different types of errors that can occur:
    * - NetworkError: Represents an error that occurred while trying to connect to the network.
    * - ServerError: Represents an error that occurred while trying to communicate with the server.
    * - TimeoutError: Represents an error that occurred due to a timeout.
    * - SerializationError: Represents an error that occurred while trying to serialize or deserialize data.
    * - UnknownError: Represents an error that occurred for an unknown reason.
    *
    * Each subclass has a message that describes the error that occurred.
 */
sealed class DomainException(message: String) : Exception(message) {
    data class NetworkError(override val message: String) : DomainException(message)
    data class ServerError(val code: Int, override val message: String) : DomainException("$code: $message")
    data class TimeoutError(override val message: String) : DomainException(message)
    data class SerializationError(override val message: String) : DomainException(message)
    data class UnknownError(override val message: String) : DomainException(message)
}