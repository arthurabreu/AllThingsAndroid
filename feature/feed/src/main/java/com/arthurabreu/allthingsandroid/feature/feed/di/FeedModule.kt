package com.arthurabreu.allthingsandroid.feature.feed.di

import androidx.room.Room
import com.arthurabreu.allthingsandroid.feature.feed.data.api.FeedApiService
import com.arthurabreu.allthingsandroid.feature.feed.data.db.FeedDatabase
import com.arthurabreu.allthingsandroid.feature.feed.data.repository.FeedRepositoryImpl
import com.arthurabreu.allthingsandroid.feature.feed.domain.repository.FeedRepository
import com.arthurabreu.allthingsandroid.feature.feed.domain.usecase.GetFeedUseCase
import com.arthurabreu.allthingsandroid.feature.feed.presentation.viewmodel.FeedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val feedModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            FeedDatabase::class.java,
            "feed_database",
        ).build()
    }

    single { get<FeedDatabase>().feedItemDao() }
    single { get<FeedDatabase>().feedRemoteKeyDao() }

    single { FeedApiService(get()) }

    single<FeedRepository> { FeedRepositoryImpl(get(), get()) }

    factory { GetFeedUseCase(get()) }

    viewModel { FeedViewModel(get()) }
}
