package com.arthurabreu.allthingsandroid.core.di.modules

import com.arthurabreu.allthingsandroid.core.MainViewModel
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigatorImpl
import com.arthurabreu.allthingsandroid.services.MyRepository
import com.arthurabreu.allthingsandroid.services.MyRepositoryImpl
import com.arthurabreu.allthingsandroid.services.MyUseCase
import com.arthurabreu.allthingsandroid.ui.features.download.DownloadViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

/*
    * This is the main module of the app.
    * Here you can define all the dependencies that will be used in the app.
 */
val appModule = module {
    // ViewModel example. Add get() to each dependency in constructor
     viewModel { MainViewModel(get(), get()) }

    // Interface example that can be used for repos, navigators, etc
    single<AppNavigator> { AppNavigatorImpl() }

    single { MyUseCase(get()) }
    single<MyRepository> { MyRepositoryImpl() }

    viewModel { DownloadViewModel() }
}

