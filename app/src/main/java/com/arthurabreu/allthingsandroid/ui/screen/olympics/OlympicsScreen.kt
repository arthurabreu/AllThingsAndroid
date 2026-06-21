package com.arthurabreu.allthingsandroid.ui.screen.olympics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppScaffold
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.ui.viewmodel.olympics.OlympicsViewModel
import com.arthurabreu.commonscreens.ui.composables.OlympicsComposable
import com.arthurabreu.commonscreens.ui.composables.shared.CenteredContentColumn
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun OlympicsScreen(
    viewModel: OlympicsViewModel = koinViewModel()
) {
    val olympicsState by viewModel.olympicsState.collectAsState()
    val appNavigator: AppNavigator = koinInject()

    AppScaffold(title = "Olympics", onBack = { appNavigator.tryNavigateBack() }) { padding ->
        CenteredContentColumn(padding = padding) {
            OlympicsComposable(state = olympicsState)
        }
    }
}