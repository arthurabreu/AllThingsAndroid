package com.arthurabreu.allthingsandroid.feature.auth.di

import com.arthurabreu.allthingsandroid.feature.auth.data.AuthRepositoryImpl
import com.arthurabreu.allthingsandroid.feature.auth.domain.repository.AuthRepository
import com.arthurabreu.allthingsandroid.feature.auth.presentation.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single { FirebaseAuth.getInstance() }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    viewModel { AuthViewModel(get()) }
}
