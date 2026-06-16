package com.arthurabreu.allthingsandroid.feature.clouddb.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurabreu.allthingsandroid.feature.clouddb.domain.model.CloudNote
import com.arthurabreu.allthingsandroid.feature.clouddb.domain.repository.CloudNoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CloudDbUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val editingNote: CloudNote? = null,
)

class CloudDbViewModel(private val repo: CloudNoteRepository) : ViewModel() {

    val notes: StateFlow<List<CloudNote>> = repo.observeNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _uiState = MutableStateFlow(CloudDbUiState())
    val uiState: StateFlow<CloudDbUiState> = _uiState.asStateFlow()

    fun addNote(title: String, content: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            runCatching { repo.addNote(title, content) }
                .onFailure { e -> _uiState.update { it.copy(error = e.message) } }
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            runCatching { repo.deleteNote(id) }
                .onFailure { e -> _uiState.update { it.copy(error = e.message) } }
        }
    }

    fun startEdit(note: CloudNote) {
        _uiState.update { it.copy(editingNote = note) }
    }

    fun saveEdit(title: String, content: String) {
        val note = _uiState.value.editingNote ?: return
        viewModelScope.launch {
            runCatching { repo.updateNote(note.copy(title = title, content = content)) }
                .onFailure { e -> _uiState.update { it.copy(error = e.message) } }
            _uiState.update { it.copy(editingNote = null) }
        }
    }

    fun cancelEdit() {
        _uiState.update { it.copy(editingNote = null) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
