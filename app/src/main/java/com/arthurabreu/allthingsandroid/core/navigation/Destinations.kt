package com.arthurabreu.allthingsandroid.core.navigation

sealed class Destination(val route: String) {
    data object Home : Destination("home")
    data class Profile(val userId: String) : Destination("profile/{userId}") {
        fun createRoute() = "profile/$userId"
    }
    data object Settings : Destination("settings")
}