package com.arthurabreu.allthingsandroid.core.di

import com.arthurabreu.allthingsandroid.core.common.AppDispatchers
import com.arthurabreu.allthingsandroid.core.common.AppLogger
import com.arthurabreu.allthingsandroid.core.common.NoOpLogger
import com.arthurabreu.allthingsandroid.core.network.HttpClients
import com.arthurabreu.allthingsandroid.core.network.LocalEchoTransport
import com.arthurabreu.allthingsandroid.core.network.VoiceTransport
import com.arthurabreu.allthingsandroid.feature.chat.ChatViewModel
import com.arthurabreu.allthingsandroid.feature.feedback.FeedbackViewModel
import com.arthurabreu.allthingsandroid.feature.firebase.FirebaseViewModel
import com.arthurabreu.allthingsandroid.feature.home.CatalogViewModel
import com.arthurabreu.allthingsandroid.feature.lab.LeaksViewModel
import com.arthurabreu.allthingsandroid.feature.lists.ListsViewModel
import com.arthurabreu.allthingsandroid.feature.maps.MapsViewModel
import com.arthurabreu.allthingsandroid.feature.persistence.PersistenceViewModel
import com.arthurabreu.allthingsandroid.feature.shop.ShopViewModel
import com.arthurabreu.allthingsandroid.feature.voice.VoiceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val portfolioModule = module {
    single { AppDispatchers() }
    single<AppLogger> { NoOpLogger() }
    single { HttpClients.create() }
    single<VoiceTransport> { LocalEchoTransport() }

    viewModel { CatalogViewModel() }
    viewModel { PersistenceViewModel() }
    viewModel { ListsViewModel() }
    viewModel { ShopViewModel() }
    viewModel { MapsViewModel() }
    viewModel { FirebaseViewModel() }
    viewModel { ChatViewModel() }
    viewModel { VoiceViewModel(get()) }
    viewModel { FeedbackViewModel() }
    viewModel { LeaksViewModel() }
}
