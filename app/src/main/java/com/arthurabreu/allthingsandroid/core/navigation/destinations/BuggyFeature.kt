package com.arthurabreu.allthingsandroid.core.navigation.destinations

/**
 * Sealed class to represent the destinations of the Buggy feature.
 */
object BuggyFeature {
    data object Buggy : NoParamsDestination("buggy")
}
