package com.arthurabreu.allthingsandroid.feature.profile.domain.repository

import com.arthurabreu.allthingsandroid.feature.profile.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    val profile: Flow<Profile>

    suspend fun updateProfile(displayName: String, bio: String): Result<Profile>
}
