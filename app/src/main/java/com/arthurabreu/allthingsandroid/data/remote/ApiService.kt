package com.arthurabreu.allthingsandroid.data.remote

import com.arthurabreu.allthingsandroid.data.remote.dto.ApiDto

interface ApiService {
    suspend fun getRawJsonResponse(): String
    suspend fun fetchData(): ApiDto
}