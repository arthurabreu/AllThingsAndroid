package com.arthurabreu.allthingsandroid.ui.screen.olympics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppScaffold
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.ui.viewmodel.olympics.OlympicsViewModel
import com.arthurabreu.commonscreens.ui.composables.OlympicsComposable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun OlympicsScreen(
    viewModel: OlympicsViewModel = koinViewModel()
) {
    val olympicsState by viewModel.olympicsState.collectAsState()
    val appNavigator: AppNavigator = koinInject()

    AppScaffold(title = "Olympics", onBack = { appNavigator.tryNavigateBack() }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OlympicsComposable(state = olympicsState)
        }
    }
}