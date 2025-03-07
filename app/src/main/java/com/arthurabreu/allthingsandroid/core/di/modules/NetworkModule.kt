package com.arthurabreu.allthingsandroid.core.di.modules

import com.arthurabreu.allthingsandroid.data.mapper.ApiMapper
import com.arthurabreu.allthingsandroid.data.remote.ApiService
import com.arthurabreu.allthingsandroid.data.remote.KtorApiServiceImpl
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

    single<ApiService> { KtorApiServiceImpl(get()) }
    factory { ApiMapper() }
    single<ApiRepository> { ApiRepositoryImpl(get(), get()) }
}