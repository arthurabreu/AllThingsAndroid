package com.arthurabreu.allthingsandroid.core

import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.domain.usecases.DataUseCases

class MainViewModel(
    appNavigator: AppNavigator,
    private val useCases: DataUseCases
) : ViewModel() {
    val navigationChannel = appNavigator.navigationChannel
}