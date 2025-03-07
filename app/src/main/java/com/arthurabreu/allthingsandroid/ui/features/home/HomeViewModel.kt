package com.arthurabreu.allthingsandroid.ui.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurabreu.allthingsandroid.core.UiState
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ProfileFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.SettingsFeature
import com.arthurabreu.allthingsandroid.domain.exception.NetworkException
import com.arthurabreu.allthingsandroid.domain.repos.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val appNavigator: AppNavigator,
    private val repository: ApiRepository
) : ViewModel() {

    private val _data = MutableStateFlow("")
    val data: StateFlow<String?> = _data
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        _data.value = "user123"
        Log.d("MainViewModel", "MainViewModel created")
        loadData()
    }

    fun onProfileClick() {
        val route = ProfileFeature.Profile(userId = _data.value)
        appNavigator.tryNavigateTo(route)
    }

    fun onSettingsClick() {
        appNavigator.tryNavigateTo(SettingsFeature.Settings.route)
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = repository.getData()
                Log.d("MainViewModel", "Data loaded")
                // Log each item or show it as JSON
                Log.d("MainViewModel", "Fetched item: ${data.title} + ${data.title}")
                _uiState.value = UiState.Success(data)
            } catch (e: Exception) {
                Log.d("MainViewModel", "loadData error $e ")
                _uiState.value = UiState.Error(
                    when (e) {
                        is NetworkException.ConnectionError -> "Check your internet connection: ${e.code}"
                        is NetworkException.ServerError -> "Server error: ${e.code}"
                        is NetworkException.TimeoutError -> "Request timed out: ${e.code}"
                        else -> "An error occurred"
                    }
                )
            }
        }
    }
}