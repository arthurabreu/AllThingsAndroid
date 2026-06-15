package com.arthurabreu.allthingsandroid.core.navigation

import kotlinx.coroutines.channels.Channel

/*
    * AppNavigator interface that defines the navigation methods and the navigation channel
    * NavigationIntent sealed class that defines the navigation actions
    * NavigateBack and NavigateTo data classes that represent the navigation actions
    * navigateBack and navigateTo methods that send the navigation actions to the navigation channel
    * tryNavigateBack and tryNavigateTo methods that send the navigation actions to the navigation channel if the channel is not full
 */
interface AppNavigator {
    val navigationChannel: Channel<NavigationIntent>

    suspend fun navigateBack(
        route: String? = null,
        inclusive: Boolean = false,
    )

    fun tryNavigateBack(
        route: String? = null,
        inclusive: Boolean = false,
    )

    suspend fun navigateTo(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )

    fun tryNavigateTo(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )
}

/*
    * NavigationIntent sealed class that defines the navigation actions
    * NavigateBack and NavigateTo data classes that represent the navigation actions
 */
sealed class NavigationIntent {
    data class NavigateBack(
        val route: String? = null,
        val inclusive: Boolean = false,
    ) : NavigationIntent()

    data class NavigateTo(
        val route: String,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false,
    ) : NavigationIntent()
}