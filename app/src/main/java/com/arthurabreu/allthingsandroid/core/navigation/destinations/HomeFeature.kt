package com.arthurabreu.allthingsandroid.core.navigation.destinations

/**
 * Sealed class to represent the destinations of the Home feature.
 *
 * Each object represents a destination that can be navigated to.
 */
object HomeFeature {
    data object Home : NoParamsDestination("home") {
        // Optional: Add feature-specific extensions here
    }

    // Example: Add onboarding screens later
//    data object Onboarding : NoParamsDestination("onboarding")
}