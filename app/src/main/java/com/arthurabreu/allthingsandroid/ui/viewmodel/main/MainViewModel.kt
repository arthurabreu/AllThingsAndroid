package com.arthurabreu.allthingsandroid.ui.viewmodel.main

import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.domain.usecases.DataUseCases

/**
 * ViewModel for the MainFragment
 *
 * @param appNavigator the navigator for the app
 * @param useCases the use cases for the app
 */
class MainViewModel(
    appNavigator: AppNavigator,
    private val useCases: DataUseCases
) : ViewModel() {
    val navigationChannel = appNavigator.navigationChannel
}