package com.arthurabreu.allthingsandroid.core.navigation.destinations

object ProfileFeature {
    data object Profile : Destination("profile", "userId") {
        private const val USER_ID = "userId"

        operator fun invoke(userId: String) =
            route.appendParams(USER_ID to userId)
    }
}