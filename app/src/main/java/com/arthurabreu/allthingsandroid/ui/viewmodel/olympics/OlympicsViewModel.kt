package com.arthurabreu.allthingsandroid.ui.viewmodel.olympics

import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.commonscreens.ui.state.olympics.OlympicsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OlympicsViewModel(
    private val appNavigator: AppNavigator,
) : ViewModel() {
    private val _olympicsState = MutableStateFlow(OlympicsState())
    val olympicsState: StateFlow<OlympicsState> = _olympicsState.asStateFlow()

    fun onBack() {
        appNavigator.tryNavigateBack()
    }
}