package com.arthurabreu.allthingsandroid.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.core.navigation.destinations.Destination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    appNavigator: AppNavigator,
) : ViewModel() {
    val navigationChannel = appNavigator.navigationChannel

    private val _navigationEvent = Channel<NavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun navigateTo(destination: Destination) {
        viewModelScope.launch {
            _navigationEvent.send(NavigationEvent.Navigate(destination))
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvent.send(NavigationEvent.Back)
        }
    }

    sealed class NavigationEvent {
        data class Navigate(val destination: Destination) : NavigationEvent()
        data object Back : NavigationEvent()
    }
}