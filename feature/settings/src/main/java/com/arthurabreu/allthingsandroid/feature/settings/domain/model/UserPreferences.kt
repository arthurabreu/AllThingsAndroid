package com.arthurabreu.allthingsandroid.feature.settings.domain.model

/**
 * Domain model representing user preferences and settings.
 *
 * Backed by DataStore persistence in the real implementation ([DataStoreSettingsRepositoryImpl]);
 * currently includes theme + notification toggles; extend as needed (language, privacy, etc.).
 */
data class UserPreferences(
    val darkThemeEnabled: Boolean = false,
    val notificationsEnabled: Boolean = true,
)
