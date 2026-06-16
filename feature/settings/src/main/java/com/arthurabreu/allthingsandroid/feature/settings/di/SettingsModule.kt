package com.arthurabreu.allthingsandroid.feature.settings.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.arthurabreu.allthingsandroid.feature.settings.data.DataStoreSettingsRepositoryImpl
import com.arthurabreu.allthingsandroid.feature.settings.domain.repository.SettingsRepository
import com.arthurabreu.allthingsandroid.feature.settings.presentation.viewmodel.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * DataStore extension for user preferences. Named "user_preferences" to distinguish from the
 * "settings" DataStore in [persistenceModule] which is used for cache staleness only.
 */
val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

val settingsModule = module {
    single { androidContext().userPreferencesDataStore }
    single<SettingsRepository> {
        DataStoreSettingsRepositoryImpl(get())
    }
    viewModel { SettingsViewModel(get()) }
}
