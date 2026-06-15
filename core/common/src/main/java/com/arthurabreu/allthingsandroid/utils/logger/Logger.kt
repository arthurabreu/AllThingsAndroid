package com.arthurabreu.allthingsandroid.utils.logger

import android.util.Log

/*
     This interface defines the contract for a Logger.
     It provides methods for logging messages at different severity levels:
     verbose, debug, info, warning, and error.
     Each method can also accept an optional map of attributes to include in the log.
     The error level also accepts an optional Throwable to log the exception details.
*/
interface Logger {
    fun v(message: String, attributes: Map<String, Any> = emptyMap())
    fun d(message: String, attributes: Map<String, Any> = emptyMap())
    fun i(message: String, attributes: Map<String, Any> = emptyMap())
    fun w(message: String, attributes: Map<String, Any> = emptyMap())
    fun e(message: String, exception: Throwable? = null, attributes: Map<String, Any> = emptyMap())
}

/*
     This class implements the Logger interface and uses Android's Log class for actual logging.
     It takes the class name as a constructor parameter, which is used as the tag for the logs.
     It formats log messages to include the calling method name (obtained from CallerResolver)
     and any provided attributes.
*/
class ClassLogger(private val className: String) : Logger {
    override fun v(message: String, attributes: Map<String, Any>) =
        log(Log.VERBOSE, message, attributes)

    override fun d(message: String, attributes: Map<String, Any>) =
        log(Log.DEBUG, message, attributes)

    override fun i(message: String, attributes: Map<String, Any>) =
        log(Log.INFO, message, attributes)

    override fun w(message: String, attributes: Map<String, Any>) =
        log(Log.WARN, message, attributes)

    override fun e(message: String, exception: Throwable?, attributes: Map<String, Any>) {
        val callerInfo = CallerResolver.getCallerInfo()
        val (tag, fullMessage) = className to "$message\n$callerInfo ${formatAttributes(attributes)}"
        Log.e(tag, fullMessage, exception)
    }

    private fun log(priority: Int, message: String, attributes: Map<String, Any>) {
        val (tag, fullMessage) = prepareLogComponents(message, attributes)
        Log.println(priority, tag, fullMessage)
    }

    private fun prepareLogComponents(
        message: String,
        attributes: Map<String, Any>
    ): Pair<String, String> {
        val callerInfo = CallerResolver.getCallerInfo()
        return className to "$message\n$callerInfo ${formatAttributes(attributes)}"
    }

    private fun formatAttributes(attributes: Map<String, Any>): String =
        if (attributes.isEmpty()) "" else "| ${attributes.entries.joinToString { "${it.key}=${it.value}" }}"
}