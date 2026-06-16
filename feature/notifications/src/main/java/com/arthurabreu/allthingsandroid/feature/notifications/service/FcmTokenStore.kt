package com.arthurabreu.allthingsandroid.feature.notifications.service

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val FCM_TOKEN_KEY = stringPreferencesKey("fcm_token")

class FcmTokenStore(private val dataStore: DataStore<Preferences>) {
    val tokenFlow: Flow<String?> = dataStore.data.map { it[FCM_TOKEN_KEY] }
    suspend fun saveToken(token: String) {
        dataStore.edit { it[FCM_TOKEN_KEY] = token }
    }
}
