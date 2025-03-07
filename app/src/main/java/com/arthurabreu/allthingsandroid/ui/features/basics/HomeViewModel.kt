package com.arthurabreu.allthingsandroid.ui.features.basics

import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.core.navigation.Destination
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
        appNavigator.tryNavigateTo(Destination.Profile(_data.value))
    }

    fun onSettingsClick() {
        appNavigator.tryNavigateTo(Destination.Settings())
    }
}