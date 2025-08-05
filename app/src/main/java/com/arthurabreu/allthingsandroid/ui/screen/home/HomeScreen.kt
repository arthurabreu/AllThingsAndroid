package com.arthurabreu.allthingsandroid.ui.screen.home

import androidx.compose.runtime.Composable
import com.arthurabreu.allthingsandroid.ui.viewmodel.home.HomeViewModel
import com.arthurabreu.commonscreens.ui.screens.generic.home.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    HomeScreen(
        onProfileClick = { viewModel.onProfileClick("userIdFake") },
        onSettingsClick = { viewModel.onSettingsClick() },
        onDownloadClick = { viewModel.onDownloadClick() },
        onButtonsClick = { viewModel.onButtonsClick() },
        onListsClick = { viewModel.onListsClick() },
        onLoginsClick = { viewModel.onLoginsClick() },
        onLoginFakeClick = { viewModel.onLoginFakeClick() },
        onTextFieldsClick = { viewModel.onTextFieldsClick() },
        onApiShowcaseClick = { viewModel.onApiShowcaseClick() },
        onMeditationUiClick = { viewModel.onMeditationUiClick() },
        onCalculatorUiClick = { viewModel.onCalculatorUiClick() },
        onSolidUiClick = { viewModel.onSolidUiClick() },
        onDesignPrincipleUiClick = { viewModel.onDesignPrincipleUiClick() },
    )
}