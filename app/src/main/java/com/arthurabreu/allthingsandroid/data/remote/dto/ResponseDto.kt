package com.arthurabreu.allthingsandroid.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
    * Data transfer object used to represent the response from the API
    * @param success: Boolean - Indicates if the request was successful
    * @param message: String - Message from the API
    * @return ResponseDto
 */
@Serializable
data class ResponseDto(
    @SerialName("success")
    val success: Boolean,

    @SerialName("message")
    val message: String
)