package com.arthurabreu.allthingsandroid.core.common

interface AppLogger {
    fun d(message: String)
    fun e(message: String, error: Throwable? = null)
}

class NoOpLogger : AppLogger {
    override fun d(message: String) = Unit
    override fun e(message: String, error: Throwable?) = Unit
}
