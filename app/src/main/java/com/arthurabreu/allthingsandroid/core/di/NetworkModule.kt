package com.arthurabreu.allthingsandroid.core.di

import com.arthurabreu.allthingsandroid.data.config.BaseUrlProvider
import com.arthurabreu.allthingsandroid.data.config.DebugBaseUrlProvider
import com.arthurabreu.allthingsandroid.data.error.ErrorHandler
import com.arthurabreu.allthingsandroid.data.error.ErrorHandlerImpl
import com.arthurabreu.allthingsandroid.data.mapper.ApiMapper
import com.arthurabreu.allthingsandroid.data.mapper.ApiMapperImpl
import com.arthurabreu.allthingsandroid.data.remote.api.ApiService
import com.arthurabreu.allthingsandroid.data.remote.api.ApiServiceKtorImpl
import com.arthurabreu.allthingsandroid.domain.repos.ApiRepository
import com.arthurabreu.allthingsandroid.domain.repos.ApiRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/*
    * This module is responsible for providing all the necessary dependencies for the network layer.
    * It provides a singleton HttpClient instance, the ApiService implementation, the ErrorHandler implementation,
    * the ApiMapper implementation, the ApiRepository implementation, and the BaseUrlProvider implementation.
    *
    * The HttpClient instance is created using the Android engine, and it has a ContentNegotiation plugin
    * for JSON parsing, a Logging plugin for logging network requests/responses, and a HttpTimeout plugin
    * for setting a timeout of 15 seconds.
 */
val networkModule = module {
    single { // Creates a singleton HttpClient instance
        HttpClient(Android) { // Uses the Android engine
            install(ContentNegotiation) { // For JSON parsing
                json(
                    contentType = ContentType.Application.Json,
                    json = Json { ignoreUnknownKeys = true } // Same as your sample!
                )
            }
            install(Logging) { // Optional: Logs network requests/responses
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
            }
            install(HttpTimeout) { // Sets timeout (15 seconds)
                requestTimeoutMillis = 15_000
            }
        }
    }
    single<BaseUrlProvider> { DebugBaseUrlProvider() }
    single<ApiService> { ApiServiceKtorImpl(get(), get()) }
    single<ErrorHandler> { ErrorHandlerImpl() }
    single<ApiMapper> { ApiMapperImpl() }
    single<ApiRepository> { ApiRepositoryImpl(get(), get(), get(), get(), get()) }
}