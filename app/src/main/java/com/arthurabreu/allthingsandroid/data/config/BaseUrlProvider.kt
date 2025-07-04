package com.arthurabreu.allthingsandroid.data.config

/*
 * Interface to provide the base URL for the API
 */
interface BaseUrlProvider {
    val baseUrl: String
}

/*
 * Base URL provider for the debug environment
 */
class DebugBaseUrlProvider : BaseUrlProvider {
    override val baseUrl: String = "https://jsonplaceholder.typicode.com"
}