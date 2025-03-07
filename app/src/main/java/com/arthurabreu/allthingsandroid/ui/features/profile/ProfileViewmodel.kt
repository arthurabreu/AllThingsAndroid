package com.arthurabreu.allthingsandroid.ui.features.profile

import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator

class ProfileViewmodel(
    private val appNavigator: AppNavigator,
) : ViewModel() {

    fun onBack() {
        appNavigator.tryNavigateBack()
    }
}