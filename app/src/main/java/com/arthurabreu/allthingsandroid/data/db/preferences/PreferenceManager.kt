package com.arthurabreu.allthingsandroid.data.db.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Manages user preferences, including the last update time, via DataStore.
 * @param dataStore DataStore instance to store preferences.
 * @property LAST_UPDATE_KEY Key to store the last update time.
 * @property lastUpdateTime Flow to observe the last update time.
 * @constructor Creates a PreferencesManager.
 * @property updateLastUpdateTime Updates the last update time.
 * @property time New last update time.
 */
class PreferencesManager(private val dataStore: DataStore<Preferences>) {
    private val LAST_UPDATE_KEY = longPreferencesKey("last_update_time")

    val lastUpdateTime: Flow<Long> = dataStore.data
        .map { preferences -> preferences[LAST_UPDATE_KEY] ?: 0L }

    suspend fun updateLastUpdateTime(time: Long) {
        dataStore.edit { preferences ->
            preferences[LAST_UPDATE_KEY] = time
        }
    }
}