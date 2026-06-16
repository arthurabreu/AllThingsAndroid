package com.arthurabreu.allthingsandroid.feature.auth.data

import com.arthurabreu.allthingsandroid.feature.auth.domain.model.AuthUser
import com.arthurabreu.allthingsandroid.feature.auth.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val auth: FirebaseAuth) : AuthRepository {

    override val currentUser: Flow<AuthUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { trySend(it.currentUser?.toDomain()) }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<AuthUser> =
        runCatching {
            auth.signInWithEmailAndPassword(email, password).await().user!!.toDomain()
        }

    override suspend fun signInWithGoogle(idToken: String): Result<AuthUser> =
        runCatching {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).await().user!!.toDomain()
        }

    override suspend fun createAccount(email: String, password: String): Result<AuthUser> =
        runCatching {
            auth.createUserWithEmailAndPassword(email, password).await().user!!.toDomain()
        }

    override suspend fun signOut() = auth.signOut()

    private fun FirebaseUser.toDomain() = AuthUser(
        uid = uid,
        email = email,
        displayName = displayName,
        photoUrl = photoUrl?.toString(),
    )
}
