package com.arthurabreu.allthingsandroid.feature.settings.domain.repository

import com.arthurabreu.allthingsandroid.feature.settings.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val preferences: Flow<UserPreferences>

    suspend fun setDarkTheme(enabled: Boolean)

    suspend fun setNotificationsEnabled(enabled: Boolean)

    suspend fun signOut(): Result<Unit>

    suspend fun deleteAccount(): Result<Unit>
}
