package com.arthurabreu.allthingsandroid.ui.features.home

import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ProfileFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.SettingsFeature
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val appNavigator: AppNavigator
) : ViewModel() {

    private val _data = MutableStateFlow("")
    val data: StateFlow<String?> = _data

    init {
        _data.value = "user123"
    }

    fun onProfileClick() {
        val route = ProfileFeature.Profile(userId = _data.value)
        appNavigator.tryNavigateTo(route)
    }

    fun onSettingsClick() {
        appNavigator.tryNavigateTo(SettingsFeature.Settings.route)
    }
}