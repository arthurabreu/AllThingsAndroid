package com.arthurabreu.allthingsandroid.feature.feed.domain.model

data class FeedItem(
    val id: Int,
    val title: String,
    val body: String,
    val imageUrl: String,
    val authorName: String,
)
