package com.arthurabreu.allthingsandroid.core.navigation.destinations

/**
 * Destinations are used to navigate between screens.
 * Each destination has a route and optional parameters.
 */
sealed class Destination(
    val route: String,
    vararg val params: String
) {
    val fullRoute: String = buildString {
        append(route)
        params.forEach { param -> append("/{${param}}") }
    }
}
// For destinations without parameters
sealed class NoParamsDestination(route: String) : Destination(route)

// Helper for appending parameters
internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)
    params.forEach { (key, value) ->
        value?.let { builder.append("/$it") }
    }
    return builder.toString()
}