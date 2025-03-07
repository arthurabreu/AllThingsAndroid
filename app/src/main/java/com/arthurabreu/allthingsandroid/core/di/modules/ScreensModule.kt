package com.arthurabreu.allthingsandroid.core.di.modules

import com.arthurabreu.allthingsandroid.ui.features.home.HomeViewModel
import com.arthurabreu.allthingsandroid.ui.features.profile.ProfileViewmodel
import com.arthurabreu.allthingsandroid.ui.features.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val screensModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ProfileViewmodel(get()) }
    viewModel { SettingsViewModel(get()) }
}