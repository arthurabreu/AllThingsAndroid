package com.arthurabreu.allthingsandroid.utils.logger

/*
     This object is responsible for resolving information about the caller method.
     The getCallerInfo() function retrieves the current thread's stack trace and attempts to
     identify the relevant calling method within the application's package,
     prioritizing coroutine's invokeSuspend methods within the UI package (likely ViewModels).
     It returns a formatted string containing a few relevant stack trace elements to provide context.
*/
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
                        currentElement.className.startsWith("com.arthurabreu.allthingsandroid.ui") // Assuming this is a package where ViewModel are declared
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

