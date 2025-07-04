package com.arthurabreu.allthingsandroid.core.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ButtonsFeature
import com.arthurabreu.allthingsandroid.ui.viewmodel.main.MainViewModel
import com.arthurabreu.allthingsandroid.core.navigation.destinations.DownloadFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.HomeFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ListsFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.LoginFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ProfileFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.SettingsFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.TextFieldsFeature
import com.arthurabreu.allthingsandroid.ui.screen.download.DownloadScreen
import com.arthurabreu.allthingsandroid.ui.screen.home.HomeScreen
import com.arthurabreu.allthingsandroid.ui.screen.login.LoginScreen
import com.arthurabreu.allthingsandroid.ui.screen.profile.ProfileScreen
import com.arthurabreu.allthingsandroid.ui.screen.settings.SettingsScreen
import com.arthurabreu.commonscreens.ui.screens.buttons.ButtonScreen
import com.arthurabreu.commonscreens.ui.screens.lists.ListsScreen
import com.arthurabreu.commonscreens.ui.screens.login.LoginScreenFake
import com.arthurabreu.commonscreens.ui.screens.textfields.AllTextFieldsScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun NavigationGraph(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavigationEffects(
        navigationChannel = viewModel.navigationChannel,
        navHostController = navController
    )

    Scaffold { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController,
                startDestination = HomeFeature.Home.fullRoute
            ) {
                composable(HomeFeature.Home.route) { HomeScreen() }
                composable(
                    route = ProfileFeature.Profile.fullRoute,
                    arguments = listOf(navArgument("userId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId").orEmpty()
                    ProfileScreen(userId)
                }
                composable(SettingsFeature.Settings.fullRoute) {
                    SettingsScreen()
                }
                composable(DownloadFeature.Download.fullRoute) {
                    DownloadScreen()
                }
                composable(ButtonsFeature.Buttons.fullRoute) {
                    ButtonScreen()
                }
                composable(ListsFeature.Lists.fullRoute)  {
                    ListsScreen()
                }
                composable(LoginFeature.Logins.fullRoute)  {
                    LoginScreen()
                }
                composable(LoginFeature.LoginFake.fullRoute)  {
                    LoginScreenFake()
                }
                composable(TextFieldsFeature.TextFields.fullRoute)  {
                    AllTextFieldsScreen()
                }
            }
        }
    }
}

@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {
    val activity = LocalActivity.current
    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }
                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }
            }
        }
    }
}