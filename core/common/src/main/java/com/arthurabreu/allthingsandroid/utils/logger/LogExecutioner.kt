package com.arthurabreu.allthingsandroid.utils.logger

// For suspend functions and non suspend functions
/*
     This suspend inline function is a utility to log the execution of a given suspending block of code.
     It takes a Logger instance and a suspending lambda (block) as input.
     It logs the start of the execution, executes the block, and then logs the completion or failure,
     including information about the calling method obtained from CallerResolver.
*/
suspend inline fun <T> logApiExecution(
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

suspend fun <T> logExecution(
    logger: Logger,
    tag: String,
    functionName: String,
    block: suspend () -> T
): T? {
    logger.d("$functionName execution started. tag: $tag")
    return try {
        val result = block()
        logger.d("$functionName execution finished successfully. tag: $tag")
        result
    } catch (e: Exception) {
        logger.e("$functionName failed with an exception. tag: $tag", e)
        null // Or rethrow the exception depending on desired behavior
    }
}

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