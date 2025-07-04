package com.arthurabreu.allthingsandroid.data.config

import com.arthurabreu.allthingsandroid.domain.exceptions.DomainException

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: DomainException) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}