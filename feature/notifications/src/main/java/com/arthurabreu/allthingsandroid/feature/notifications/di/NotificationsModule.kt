package com.arthurabreu.allthingsandroid.feature.notifications.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.arthurabreu.allthingsandroid.feature.notifications.presentation.viewmodel.NotificationsViewModel
import com.arthurabreu.allthingsandroid.feature.notifications.service.FcmTokenStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val Context.notifDataStore by preferencesDataStore(name = "notifications_prefs")

val notificationsModule = module {
    single { FcmTokenStore(androidContext().notifDataStore) }
    viewModel { NotificationsViewModel(get(), androidContext()) }
}
