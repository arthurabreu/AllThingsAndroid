package com.arthurabreu.allthingsandroid.data.config

interface BaseUrlProvider {
    val baseUrl: String
}

class DebugBaseUrlProvider : BaseUrlProvider {
    override val baseUrl: String = "https://jsonplaceholder.typicode.com"
}