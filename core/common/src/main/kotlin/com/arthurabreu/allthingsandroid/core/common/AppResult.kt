package com.arthurabreu.allthingsandroid.core.common

sealed class AppResult<out T> {
    data class Ok<T>(val value: T) : AppResult<T>()
    data class Err(val message: String, val cause: Throwable? = null) : AppResult<Nothing>()

    val isOk: Boolean get() = this is Ok
    fun getOrNull(): T? = (this as? Ok)?.value
}

inline fun <T> runAppCatching(block: () -> T): AppResult<T> =
    try {
        AppResult.Ok(block())
    } catch (t: Throwable) {
        AppResult.Err(t.message ?: "Unknown error", t)
    }
