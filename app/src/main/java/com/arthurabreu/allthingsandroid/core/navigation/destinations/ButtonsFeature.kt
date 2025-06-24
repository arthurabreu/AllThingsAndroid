package com.arthurabreu.allthingsandroid.core.navigation.destinations

object ButtonsFeature {
    data object Buttons : NoParamsDestination("buttons_example") {
        override fun toString(): String {
            return "ButtonsExample"
        }
    }
}