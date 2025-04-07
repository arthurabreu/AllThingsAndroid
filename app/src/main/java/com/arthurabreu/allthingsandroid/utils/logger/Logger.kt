package com.arthurabreu.allthingsandroid.utils.logger

import android.util.Log

interface Logger {
    fun v(message: String, attributes: Map<String, Any> = emptyMap())
    fun d(message: String, attributes: Map<String, Any> = emptyMap())
    fun i(message: String, attributes: Map<String, Any> = emptyMap())
    fun w(message: String, attributes: Map<String, Any> = emptyMap())
    fun e(message: String, exception: Throwable? = null, attributes: Map<String, Any> = emptyMap())
}

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

/*
The CallerResolver class retrieves the first non-excluded caller method name from the stack trace.
It checks each element in the stack trace and skips any class that starts with certain packages.
If you add your own package to that exclusion list, any classes within it (e.g.: HomeViewModel)
will be skipped and won’t appear in the resolved method name.
 */
//object CallerResolver {
//    fun getCallerMethodName(): String {
//        val stackTrace = Thread.currentThread().stackTrace
//        stackTrace.forEach { element ->
//            if (element.className.startsWith("com.arthurabreu.allthingsandroid") && // Replace with your app's package
//                !element.className.startsWith("com.arthurabreu.allthingsandroid.utils.logger") && // Exclude logging package
//                !element.className.startsWith("kotlin.coroutines") &&
//                !element.className.startsWith("kotlinx.coroutines") &&
//                !element.className.startsWith("java.lang")
//            ) {
//                return element.methodName
//            }
//        }
//        return "UnknownMethod"
//    }
//}
object CallerResolver {
    fun getCallerInfo(): String {
        val stackTrace = Thread.currentThread().stackTrace
        val relevantFrames = mutableListOf<String>()

        stackTrace.forEachIndexed { index, element ->
            if (element.className.startsWith("com.arthurabreu.allthingsandroid") &&
                !element.className.startsWith("com.arthurabreu.allthingsandroid.utils") &&
                !element.className.startsWith("com.arthurabreu.allthingsandroid.core") &&
                !element.className.startsWith("com.arthurabreu.allthingsandroid.data") &&
                !element.className.startsWith("com.arthurabreu.allthingsandroid.services")
            ) {
                // Found a frame within the app's package
                // Check if this frame or the next few are invokeSuspend within a ViewModel's package
                for (i in index..minOf(index + 2, stackTrace.size - 1)) {
                    val currentElement = stackTrace[i]
                    if (currentElement.methodName == "invokeSuspend" &&
                        currentElement.className.startsWith("com.arthurabreu.allthingsandroid.ui") // Assuming HomeViewModel package
                    ) {
                        val startIndex = (i - 1).coerceAtLeast(0)
                        val endIndex = (i + 1).coerceAtMost(stackTrace.size - 1)
                        for (j in startIndex..endIndex) {
                            val frame = stackTrace[j]
                            val className = frame.className
                            val methodName = frame.methodName
                            val prefix = if (j == i) "-> " else "   "
                            relevantFrames.add("$prefix${className.substringAfterLast('.')}.${methodName}()")
                        }
                        return relevantFrames.joinToString("\n")
                    }
                }

                // If no invokeSuspend found nearby, just show the initial app frame
                val startIndex = (index - 1).coerceAtLeast(0)
                val endIndex = (index + 1).coerceAtMost(stackTrace.size - 1)
                for (i in startIndex..endIndex) {
                    val frame = stackTrace[i]
                    val className = frame.className
                    val methodName = frame.methodName
                    val prefix = if (i == index) "-> " else "   "
                    relevantFrames.add("$prefix${className.substringAfterLast('.')}.${methodName}()")
                }
                return relevantFrames.joinToString("\n")
            }
        }

        return "Could not resolve caller information."
    }
}

// This implementation uses the stack trace to find the first method that is not part of the logger package.
// It assumes that the logger package is "co.stone.multiplatform.logger". Adjust this if your logger package is different.

// For non suspend functions
//inline fun <T> logExecution(
//    logger: Logger,
//    crossinline block: () -> T
//): T {
//    val methodName = CallerResolver.getCallerMethodName()
//    logger.d("$methodName - Started")
//    return try {
//        val result = block()
//        logger.d("$methodName - Completed")
//        result
//    } catch (e: Exception) {
//        logger.e("$methodName - Failed", e)
//        throw e
//    }
//}

// For suspend functions
suspend inline fun <T> logExecution(
    logger: Logger,
    crossinline block: suspend () -> T
): T {
    val callerInfo = CallerResolver.getCallerInfo()
    logger.d("Execution Started:\n$callerInfo")
    return try {
        val result = block()
        val completionCallerInfo = CallerResolver.getCallerInfo()
        logger.d("Execution Completed:\n$completionCallerInfo")
        result
    } catch (e: Exception) {
        val failureCallerInfo = CallerResolver.getCallerInfo()
        logger.e("Execution Failed:\n$failureCallerInfo", e)
        throw e
    }
}