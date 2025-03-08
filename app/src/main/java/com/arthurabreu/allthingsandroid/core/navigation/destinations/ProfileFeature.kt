package com.arthurabreu.allthingsandroid.core.navigation.destinations

/**
 * Sealed class that represents a destination in the app.
 *
 * @param route the route of the destination
 * @param params the parameters of the destination
 */
object ProfileFeature {
    data object Profile : Destination("profile", "userId") {
        private const val USER_ID = "userId"

        operator fun invoke(userId: String) =
            route.appendParams(USER_ID to userId)
    }
}