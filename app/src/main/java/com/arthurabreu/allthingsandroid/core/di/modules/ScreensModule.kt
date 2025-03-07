package com.arthurabreu.allthingsandroid.core.di.modules

import com.arthurabreu.allthingsandroid.ui.features.basics.HomeViewModel
import com.arthurabreu.allthingsandroid.ui.features.basics.ProfileViewmodel
import com.arthurabreu.allthingsandroid.ui.features.basics.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val screensModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { ProfileViewmodel(get()) }
    viewModel { SettingsViewModel(get()) }
}