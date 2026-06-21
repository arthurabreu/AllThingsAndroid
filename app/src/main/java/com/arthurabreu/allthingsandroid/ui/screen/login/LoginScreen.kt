package com.arthurabreu.allthingsandroid.ui.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppScaffold
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.ui.viewmodel.login.LoginViewModel
import com.arthurabreu.commonscreens.ui.composables.login.LoginComposable
import com.arthurabreu.commonscreens.ui.composables.shared.CenteredContentColumn
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel()
) {
    val loginState by viewModel.loginState.collectAsState()
    val appNavigator: AppNavigator = koinInject()

    AppScaffold(title = "Login", onBack = { appNavigator.tryNavigateBack() }) { padding ->
        CenteredContentColumn(padding = padding) {
            LoginComposable(state = loginState)
        }
    }
}