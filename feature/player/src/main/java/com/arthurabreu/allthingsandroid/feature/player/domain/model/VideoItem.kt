package com.arthurabreu.allthingsandroid.feature.player.domain.model

data class VideoItem(
    val id: String,
    val title: String,
    val description: String,
    val uri: String,
    val thumbnailUrl: String,
)
