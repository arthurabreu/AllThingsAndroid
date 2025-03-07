package com.arthurabreu.allthingsandroid.data.remote

import com.arthurabreu.allthingsandroid.data.remote.dto.ApiDto
import com.arthurabreu.allthingsandroid.data.remote.dto.ResponseDto

interface ApiService {
    suspend fun fetchData(): ApiDto
    suspend fun sendData(data: ApiDto): ResponseDto
}