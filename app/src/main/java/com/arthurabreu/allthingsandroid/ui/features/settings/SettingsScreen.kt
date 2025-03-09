package com.arthurabreu.allthingsandroid.ui.features.settings

import androidx.compose.runtime.Composable
import com.arthurabreu.commonscreens.features.genericscreens.settings.SettingsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel()
) {
    SettingsScreen(
        onBack = { viewModel.onBackButtonClicked() }
    )
}