package com.arthurabreu.allthingsandroid.feature.auth.domain.repository

import com.arthurabreu.allthingsandroid.feature.auth.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<AuthUser?>
    suspend fun signInWithEmail(email: String, password: String): Result<AuthUser>
    suspend fun signInWithGoogle(idToken: String): Result<AuthUser>
    suspend fun createAccount(email: String, password: String): Result<AuthUser>
    suspend fun signOut()
}
