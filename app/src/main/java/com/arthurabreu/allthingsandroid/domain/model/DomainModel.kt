package com.arthurabreu.allthingsandroid.domain.model

data class DomainModel(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean,
)