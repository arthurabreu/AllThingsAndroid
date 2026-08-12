package com.arthurabreu.allthingsandroid.domain.repos

import com.arthurabreu.allthingsandroid.domain.model.DomainModel

/**
 * Repository interface that defines the methods that the repository should implement
 */
interface ApiRepository {
    suspend fun getData(): DomainModel
}