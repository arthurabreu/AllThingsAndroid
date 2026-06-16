package com.arthurabreu.allthingsandroid.feature.profile.di

import com.arthurabreu.allthingsandroid.feature.profile.data.FakeProfileRepositoryImpl
import com.arthurabreu.allthingsandroid.feature.profile.domain.repository.ProfileRepository
import com.arthurabreu.allthingsandroid.feature.profile.presentation.viewmodel.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    single<ProfileRepository> { FakeProfileRepositoryImpl() }
    viewModel { ProfileViewModel(get()) }
}
