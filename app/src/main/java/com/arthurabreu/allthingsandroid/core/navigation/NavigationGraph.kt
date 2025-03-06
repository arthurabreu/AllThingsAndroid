package com.arthurabreu.allthingsandroid.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arthurabreu.allthingsandroid.core.MainViewModel
import com.arthurabreu.allthingsandroid.core.MainViewModel.NavigationEvent.Back
import com.arthurabreu.allthingsandroid.core.MainViewModel.NavigationEvent.Navigate
import com.arthurabreu.allthingsandroid.ui.features.basics.HomeScreen
import com.arthurabreu.allthingsandroid.ui.features.basics.ProfileScreen
import com.arthurabreu.allthingsandroid.ui.features.basics.SettingsScreen

@Composable
fun NavigationGraph(viewModel: MainViewModel) {
    val navController = rememberNavController()

    HandleNavigation(navController, viewModel)

    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Destination.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Destination.Home.route) {
                HomeScreen(
                    onProfileClick = { userId ->
                        viewModel.navigateTo(Destination.Profile(userId))
                    },
                    onSettingsClick = { viewModel.navigateTo(Destination.Settings) }
                )
            }
            composable(
                route = Destination.Profile("{userId}").route,
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
            ) {
                ProfileScreen(
                    userId = it.arguments?.getString("userId") ?: "",
                    onBack = { viewModel.navigateBack() }
                )
            }
            composable(Destination.Settings.route) {
                SettingsScreen(onBack = { viewModel.navigateBack() })
            }
        }
    }
}


@Composable
private fun HandleNavigation(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is Navigate -> {
                    when (val dest = event.destination) {
                        is Destination.Home -> navController.navigate(Destination.Home.route) {
                            popUpTo(Destination.Home.route) { inclusive = true }
                        }
                        is Destination.Profile -> navController.navigate(dest.createRoute())
                        is Destination.Settings -> navController.navigate(Destination.Settings.route)
                    }
                }
                is Back -> navController.popBackStack()
            }
        }
    }
}