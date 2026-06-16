package com.arthurabreu.allthingsandroid.feature.profile.data

import com.arthurabreu.allthingsandroid.feature.profile.domain.model.Profile
import com.arthurabreu.allthingsandroid.feature.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Fake, in-memory [ProfileRepository] used until a real Firestore-backed implementation
 * is wired up. Returns canned data for a single "current user" so the screen can be
 * built and tested against a realistic shape today.
 *
 * TODO: replace with a Firestore-backed impl reading "users/{uid}" once the real
 * Firebase profile document schema is decided.
 */
class FakeProfileRepositoryImpl : ProfileRepository {

    private val _profile = MutableStateFlow(
        Profile(
            uid = "fake-uid-001",
            displayName = "Arthur Abreu",
            email = "arthurabreupro@gmail.com",
            photoUrl = null,
            bio = "Android dev tinkering with Compose, Koin and Ktor.",
            joinDate = "March 2024",
            postsCount = 12,
            followersCount = 348,
            followingCount = 97,
        ),
    )

    override val profile: Flow<Profile> = _profile.asStateFlow()

    override suspend fun updateProfile(displayName: String, bio: String): Result<Profile> =
        runCatching {
            _profile.update { it.copy(displayName = displayName, bio = bio) }
            _profile.value
        }
}
