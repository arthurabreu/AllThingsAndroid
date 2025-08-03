package com.arthurabreu.allthingsandroid.ui.screen.calculator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arthurabreu.allthingsandroid.ui.viewmodel.calculator.CalculatorViewModel
import org.koin.androidx.compose.koinViewModel
@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = koinViewModel()
) {
    Calculator(
        state = viewModel.state,
        modifier = Modifier,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}