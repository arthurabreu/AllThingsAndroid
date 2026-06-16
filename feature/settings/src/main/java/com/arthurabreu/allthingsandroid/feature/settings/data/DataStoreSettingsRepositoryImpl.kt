package com.arthurabreu.allthingsandroid.feature.settings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.arthurabreu.allthingsandroid.feature.settings.domain.model.UserPreferences
import com.arthurabreu.allthingsandroid.feature.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Real [SettingsRepository] backed by DataStore for persistent local storage of user preferences.
 * Toggle state persists across app restarts via DataStore keys.
 *
 * [signOut] and [deleteAccount] are currently fake no-op stubs returning [Result.success];
 * TODO: wire these to [feature/auth AuthRepository.signOut()] once session integration lands.
 */
class DataStoreSettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : SettingsRepository {

    companion object {
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_enabled")
        private val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_enabled")
    }

    override val preferences: Flow<UserPreferences> = dataStore.data.map { prefs ->
        UserPreferences(
            darkThemeEnabled = prefs[DARK_THEME_KEY] ?: false,
            notificationsEnabled = prefs[NOTIFICATIONS_KEY] ?: true,
        )
    }

    override suspend fun setDarkTheme(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[DARK_THEME_KEY] = enabled
        }
    }

    override suspend fun setNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[NOTIFICATIONS_KEY] = enabled
        }
    }

    override suspend fun signOut(): Result<Unit> =
        // TODO: integrate with feature/auth AuthRepository.signOut()
        Result.success(Unit)

    override suspend fun deleteAccount(): Result<Unit> =
        // TODO: integrate with feature/auth AuthRepository + backend
        Result.success(Unit)
}
