package com.arthurabreu.allthingsandroid.ui.screen.settings

import androidx.compose.runtime.Composable
import com.arthurabreu.allthingsandroid.ui.viewmodel.settings.SettingsViewModel
import com.arthurabreu.commonscreens.ui.screens.generic.settings.SettingsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel()
) {
    SettingsScreen(
        onBack = { viewModel.onBackButtonClicked() }
    )
}