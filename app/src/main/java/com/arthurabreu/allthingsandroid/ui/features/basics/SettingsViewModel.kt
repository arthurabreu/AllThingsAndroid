package com.arthurabreu.allthingsandroid.ui.features.basics

import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator

class SettingsViewModel(
    private val appNavigator: AppNavigator
) : ViewModel() {

    fun onBackButtonClicked() {
        appNavigator.tryNavigateBack()
    }
}