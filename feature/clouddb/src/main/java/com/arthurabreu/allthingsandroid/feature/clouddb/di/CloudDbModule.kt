package com.arthurabreu.allthingsandroid.feature.clouddb.di

import com.arthurabreu.allthingsandroid.feature.clouddb.data.CloudNoteRepositoryImpl
import com.arthurabreu.allthingsandroid.feature.clouddb.domain.repository.CloudNoteRepository
import com.arthurabreu.allthingsandroid.feature.clouddb.presentation.viewmodel.CloudDbViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val cloudDbModule = module {
    single { FirebaseFirestore.getInstance() }
    single<CloudNoteRepository> { CloudNoteRepositoryImpl(get(), get<FirebaseAuth>()) }
    viewModel { CloudDbViewModel(get()) }
}
