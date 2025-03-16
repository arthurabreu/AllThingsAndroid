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
import com.arthurabreu.allthingsandroid.main.MainViewModel
import com.arthurabreu.allthingsandroid.core.MainViewModel
import com.arthurabreu.allthingsandroid.core.navigation.destinations.DownloadFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.HomeFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ProfileFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.SettingsFeature
import com.arthurabreu.allthingsandroid.ui.features.download.DownloadScreen
import com.arthurabreu.allthingsandroid.ui.features.home.HomeScreen
import com.arthurabreu.allthingsandroid.ui.features.profile.ProfileScreen
import com.arthurabreu.allthingsandroid.ui.features.settings.SettingsScreen
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