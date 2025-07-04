package com.arthurabreu.allthingsandroid.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.arthurabreu.allthingsandroid.domain.model.DomainData
import com.arthurabreu.allthingsandroid.ui.viewmodel.home.HomeViewModel
import com.arthurabreu.commonscreens.ui.screens.generic.home.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val domainData = viewModel.observedData.collectAsState(
        initial = DomainData(0, 0, "Default", true)
    )

    HomeScreen(
        onProfileClick = { viewModel.onProfileClick() },
        onSettingsClick = { viewModel.onSettingsClick() },
        onDownloadClick = { viewModel.onDownloadClick() },
        onButtonsClick = { viewModel.onButtonsClick() },
        onListsClick = { viewModel.onListsClick() },
        onLoginsClick = { viewModel.onLoginsClick() },
        onLoginFakeClick = { viewModel.onLoginFakeClick() },
        onTextFieldsClick = { viewModel.onTextFieldsClick() },
        json = domainData.value.toString()
    )
}