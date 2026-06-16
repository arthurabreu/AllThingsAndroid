package com.arthurabreu.allthingsandroid.ui.screen.designprinciple

import androidx.compose.runtime.Composable
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppScaffold
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import com.arthurabreu.allthingsandroid.ui.viewmodel.designprinciple.DesignPrincipleViewModel

@Composable
fun DesignPrincipleScreen(
    viewModel: DesignPrincipleViewModel = koinViewModel()
) {
    val appNavigator: AppNavigator = koinInject()

    AppScaffold(title = "Design Principles", onBack = { appNavigator.tryNavigateBack() }) {
        DesignPrincipleComponent(
            principles = viewModel.principles
        )
    }
}