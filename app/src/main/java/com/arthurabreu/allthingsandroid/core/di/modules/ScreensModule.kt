package com.arthurabreu.allthingsandroid.core.di.modules

import com.arthurabreu.allthingsandroid.ui.features.home.HomeViewModel
import com.arthurabreu.allthingsandroid.ui.features.profile.ProfileViewmodel
import com.arthurabreu.allthingsandroid.ui.features.settings.SettingsViewModel
import io.ktor.http.parametersOf
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

/*
    * This module is responsible for providing the viewmodels used in the application.
    * The viewmodels are responsible for handling the data and logic of the screens.
    * The viewmodels are created using the Koin library.
    * The viewmodels are created using the viewModel function from the Koin library.
    * The viewmodels are created using the get function from the Koin library.
    * The viewmodels are created using the module function from the Koin library.
 */
val screensModule = module {
    viewModel {
        HomeViewModel(
            get(),
            get(),
            get(),
            logger = get(parameters = { parametersOf(HomeViewModel::class.java.simpleName) })
    ) }

    viewModel { ProfileViewmodel(get()) }
    viewModel { SettingsViewModel(get()) }
}