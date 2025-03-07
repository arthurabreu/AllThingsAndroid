package com.arthurabreu.allthingsandroid.core.di.modules

import com.arthurabreu.allthingsandroid.core.MainViewModel
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigatorImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // ViewModel example. Add get() to each dependency in constructor
     viewModel { MainViewModel(get()) }

    // Interface example that can be used for repos, navigators, etc
    single<AppNavigator> { AppNavigatorImpl() }
}