package com.arthurabreu.allthingsandroid.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(
    @SerialName("success")
    val success: Boolean,

    @SerialName("message")
    val message: String
)