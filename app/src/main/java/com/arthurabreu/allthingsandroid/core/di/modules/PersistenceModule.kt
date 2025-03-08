package com.arthurabreu.allthingsandroid.core.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.arthurabreu.allthingsandroid.data.local.database.AppDatabase
import com.arthurabreu.allthingsandroid.data.local.database.AppDatabaseImpl
import com.arthurabreu.allthingsandroid.data.local.preferences.DataRepositoryImpl
import com.arthurabreu.allthingsandroid.data.local.preferences.PreferencesManager
import com.arthurabreu.allthingsandroid.data.mapper.DataMapper
import com.arthurabreu.allthingsandroid.data.mapper.DataMapperImpl
import com.arthurabreu.allthingsandroid.domain.repos.DataRepository
import com.arthurabreu.allthingsandroid.domain.usecases.DataUseCases
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val persistenceModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app-database"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<AppDatabase>().dataDao() }

    single { AppDatabaseImpl(get(), get()) }

    single<DataStore<Preferences>> { androidContext().dataStore }

    single { PreferencesManager(get()) }

    single<DataRepository> { DataRepositoryImpl(get(), get(), get()) }

    single<DataMapper> { DataMapperImpl() }

    factory { DataUseCases(get()) }

}