package com.arthurabreu.allthingsandroid.ui.features.basics

import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator

class ProfileViewmodel(
    private val appNavigator: AppNavigator,
) : ViewModel() {

    fun onBack() {
        appNavigator.tryNavigateBack()
    }
}