package com.arthurabreu.allthingsandroid.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurabreu.allthingsandroid.feature.profile.domain.model.Profile
import com.arthurabreu.allthingsandroid.feature.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val profile: Profile? = null,
    val isLoading: Boolean = true,
    val isEditing: Boolean = false,
    val error: String? = null,
)

class ProfileViewModel(private val repo: ProfileRepository) : ViewModel() {

    private val _editState = MutableStateFlow(false to (null as String?))

    val uiState: StateFlow<ProfileUiState> = repo.profile
        .combine(_editState) { profile, (isEditing, error) ->
            ProfileUiState(profile = profile, isLoading = false, isEditing = isEditing, error = error)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ProfileUiState())

    fun startEdit() {
        _editState.update { (_, error) -> true to error }
    }

    fun cancelEdit() {
        _editState.update { (_, error) -> false to error }
    }

    fun saveEdit(displayName: String, bio: String) {
        if (displayName.isBlank()) return
        viewModelScope.launch {
            repo.updateProfile(displayName, bio)
                .onSuccess { _editState.update { false to null } }
                .onFailure { e -> _editState.update { (isEditing, _) -> isEditing to e.message } }
        }
    }

    fun clearError() {
        _editState.update { (isEditing, _) -> isEditing to null }
    }
}
