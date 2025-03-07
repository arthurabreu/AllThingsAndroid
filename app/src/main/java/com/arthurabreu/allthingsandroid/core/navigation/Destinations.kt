package com.arthurabreu.allthingsandroid.core.navigation

sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    data object Home : NoArgumentsDestination("home")

    data object Settings : NoArgumentsDestination("users")

    data object Profile : Destination("profile", "userId") {
        private const val USER_ID = "userId"

        operator fun invoke(userId: String): String = route.appendParams(
            USER_ID to userId
        )
    }
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}