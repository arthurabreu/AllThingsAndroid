package com.arthurabreu.allthingsandroid.core.di.modules

import com.arthurabreu.allthingsandroid.core.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // A repository example
    // singleOf(::UserRepositoryImpl) { bind<UserRepository>() }

    // ViewModel example. Add get() to each dependency in constructor
     viewModel { MainViewModel() }
}