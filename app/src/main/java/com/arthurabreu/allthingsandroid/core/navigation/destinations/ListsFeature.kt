package com.arthurabreu.allthingsandroid.core.navigation.destinations

object ListsFeature {
    data object Lists : NoParamsDestination("lists_example") {
        override fun toString(): String {
            return "ListsExample"
        }
    }
}