package com.arthurabreu.allthingsandroid.feature.feed.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: Int,
    @SerialName("userId") val userId: Int,
    val title: String,
    val body: String,
)

@Serializable
data class PhotoDto(
    val id: Int,
    @SerialName("thumbnailUrl") val thumbnailUrl: String,
    val title: String,
)
