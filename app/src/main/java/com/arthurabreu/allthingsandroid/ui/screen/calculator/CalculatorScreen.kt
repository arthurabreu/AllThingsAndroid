package com.arthurabreu.allthingsandroid.ui.screen.calculator

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppScaffold
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.ui.viewmodel.calculator.CalculatorViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = koinViewModel()
) {
    val appNavigator: AppNavigator = koinInject()

    AppScaffold(title = "Calculator", onBack = { appNavigator.tryNavigateBack() }) { padding ->
        Calculator(
            state = viewModel.state,
            modifier = Modifier.padding(padding),
            onAction = { action ->
                viewModel.onAction(action)
            }
        )
    }
}