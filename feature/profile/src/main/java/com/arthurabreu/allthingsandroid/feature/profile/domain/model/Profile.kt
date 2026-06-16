package com.arthurabreu.allthingsandroid.feature.profile.domain.model

/**
 * Domain model representing a user's profile.
 *
 * Currently backed by a fake in-memory repository ([FakeProfileRepositoryImpl]); the
 * shape mirrors what a Firestore "users/{uid}" document would hold so swapping in a
 * real Firebase-backed repository later only requires a new repository impl, not a
 * model/UI change.
 */
data class Profile(
    val uid: String,
    val displayName: String,
    val email: String,
    val photoUrl: String?,
    val bio: String,
    val joinDate: String,
    val postsCount: Int,
    val followersCount: Int,
    val followingCount: Int,
)
