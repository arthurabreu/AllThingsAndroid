package com.arthurabreu.allthingsandroid.core

import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.domain.model.DomainModel

sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: DomainModel) : UiState()
    data class Error(val message: String) : UiState()
}

class MainViewModel(
    appNavigator: AppNavigator
) : ViewModel() {
    val navigationChannel = appNavigator.navigationChannel
}