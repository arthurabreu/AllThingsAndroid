package com.arthurabreu.allthingsandroid.core.navigation.destinations

/**
 * Sealed class that represents the destinations of the Settings feature.
 */
object SettingsFeature {
    data object Settings : NoParamsDestination("settings")
}