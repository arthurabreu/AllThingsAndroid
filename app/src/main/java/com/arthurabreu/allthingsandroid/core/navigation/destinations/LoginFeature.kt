package com.arthurabreu.allthingsandroid.core.navigation.destinations

object LoginFeature {
    data object Logins : NoParamsDestination("login_example") {
        override fun toString(): String {
            return "LoginsExample"
        }
    }
    data object LoginFake : NoParamsDestination("login_fake_example") {
        override fun toString(): String {
            return "LoginFakeExample"
        }
    }
}