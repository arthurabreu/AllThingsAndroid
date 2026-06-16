package com.arthurabreu.allthingsandroid.feature.clouddb.data

import com.arthurabreu.allthingsandroid.feature.clouddb.domain.model.CloudNote
import com.arthurabreu.allthingsandroid.feature.clouddb.domain.repository.CloudNoteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

private const val COLLECTION = "notes"

class CloudNoteRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : CloudNoteRepository {

    private val collection get() = firestore.collection(COLLECTION)

    override fun observeNotes(): Flow<List<CloudNote>> = callbackFlow {
        val listener = collection
            .orderBy("timestampMs", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) { close(error); return@addSnapshotListener }
                val notes = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(CloudNote::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(notes)
            }
        awaitClose { listener.remove() }
    }

    override suspend fun addNote(title: String, content: String) {
        val note = hashMapOf(
            "title" to title,
            "content" to content,
            "authorUid" to (auth.currentUser?.uid ?: "anonymous"),
            "timestampMs" to System.currentTimeMillis(),
        )
        collection.add(note).await()
    }

    override suspend fun updateNote(note: CloudNote) {
        collection.document(note.id).set(note).await()
    }

    override suspend fun deleteNote(id: String) {
        collection.document(id).delete().await()
    }
}
