package com.arthurabreu.allthingsandroid.ui.features.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.arthurabreu.allthingsandroid.domain.model.DomainData
import com.arthurabreu.commonscreens.features.genericscreens.home.HomeScreen
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
        json = domainData.value.toString()
    )
}