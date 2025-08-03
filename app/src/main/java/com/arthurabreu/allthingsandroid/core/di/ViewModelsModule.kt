package com.arthurabreu.allthingsandroid.core.di

import com.arthurabreu.allthingsandroid.ui.viewmodel.apishowcase.ApiShowcaseViewModel
import com.arthurabreu.allthingsandroid.ui.viewmodel.calculator.CalculatorViewModel
import com.arthurabreu.allthingsandroid.ui.viewmodel.download.DownloadViewModel
import com.arthurabreu.allthingsandroid.ui.viewmodel.home.HomeViewModel
import com.arthurabreu.allthingsandroid.ui.viewmodel.login.LoginViewModel
import com.arthurabreu.allthingsandroid.ui.viewmodel.profile.ProfileViewmodel
import com.arthurabreu.allthingsandroid.ui.viewmodel.settings.SettingsViewModel
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
val viewModelsModule = module {
    viewModel {
        HomeViewModel(
            get(),
            logger = get(parameters = { parametersOf(HomeViewModel::class.java.simpleName) })
    ) }

    viewModel { ProfileViewmodel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { DownloadViewModel() }
    viewModel {
        ApiShowcaseViewModel(
            repository = get(),
            useCases = get(),
            logger = get { parametersOf("ApiShowcaseViewModel") }
        )
    }
    viewModel { CalculatorViewModel() }
}