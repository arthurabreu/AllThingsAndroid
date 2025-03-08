package com.arthurabreu.allthingsandroid.data.remote.api

import com.arthurabreu.allthingsandroid.data.remote.dto.ApiDto

/*
 * Interface to define the methods that the API service should implement
 */
interface ApiService {
    suspend fun getRawJsonResponse(): String
    suspend fun fetchData(): ApiDto
}