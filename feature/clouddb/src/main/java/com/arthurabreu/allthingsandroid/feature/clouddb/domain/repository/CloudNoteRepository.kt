package com.arthurabreu.allthingsandroid.feature.clouddb.domain.repository

import com.arthurabreu.allthingsandroid.feature.clouddb.domain.model.CloudNote
import kotlinx.coroutines.flow.Flow

interface CloudNoteRepository {
    fun observeNotes(): Flow<List<CloudNote>>
    suspend fun addNote(title: String, content: String)
    suspend fun updateNote(note: CloudNote)
    suspend fun deleteNote(id: String)
}
