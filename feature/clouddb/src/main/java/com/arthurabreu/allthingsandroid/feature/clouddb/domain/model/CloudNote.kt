package com.arthurabreu.allthingsandroid.feature.clouddb.domain.model

data class CloudNote(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val authorUid: String = "",
    val timestampMs: Long = 0L,
)
