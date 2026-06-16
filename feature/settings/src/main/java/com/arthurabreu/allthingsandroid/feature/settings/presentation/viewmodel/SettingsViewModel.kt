package com.arthurabreu.allthingsandroid.feature.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurabreu.allthingsandroid.feature.settings.domain.model.UserPreferences
import com.arthurabreu.allthingsandroid.feature.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SettingsUiState(
    val preferences: UserPreferences = UserPreferences(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

class SettingsViewModel(private val repo: SettingsRepository) : ViewModel() {

    private val _errorState = MutableStateFlow<String?>(null)

    val uiState: StateFlow<SettingsUiState> = repo.preferences
        .combine(_errorState) { prefs, error ->
            SettingsUiState(preferences = prefs, isLoading = false, error = error)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SettingsUiState())

    fun toggleDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            repo.setDarkTheme(enabled)
        }
    }

    fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch {
            repo.setNotificationsEnabled(enabled)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            repo.signOut()
                .onFailure { e -> _errorState.update { e.message } }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            repo.deleteAccount()
                .onFailure { e -> _errorState.update { e.message } }
        }
    }

    fun clearError() {
        _errorState.update { null }
    }
}
