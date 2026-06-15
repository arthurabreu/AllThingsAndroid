package com.arthurabreu.allthingsandroid.core.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel

/*
    * AppNavigatorImpl is a class that implements the AppNavigator interface.
    * It is responsible for handling navigation intents and sending them to the navigation channel.
    * The navigation channel is a Channel that is used to send navigation intents to the navigation component.
    * The navigation intents are used to navigate between screens in the app.
    * The navigateBack function is used to navigate back to the previous screen.
    * The navigateTo function is used to navigate to a new screen.
    * The tryNavigateBack and tryNavigateTo functions are used to try to navigate back or to a new screen, respectively.
    * The navigation intents are sent to the navigation channel using the send and trySend functions.
    * The send function sends the intent to the channel, blocking if the channel is full.
    * The trySend function tries to send the intent to the channel, returning false if the channel is full.
 */
class AppNavigatorImpl() : AppNavigator {
    override val navigationChannel = Channel<NavigationIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    override suspend fun navigateBack(route: String?, inclusive: Boolean) {
        navigationChannel.send(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = inclusive
            )
        )
    }

    override fun tryNavigateBack(route: String?, inclusive: Boolean) {
        navigationChannel.trySend(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = inclusive
            )
        )
    }

    override suspend fun navigateTo(
        route: String,
        popUpToRoute: String?,
        inclusive: Boolean,
        isSingleTop: Boolean
    ) {
        navigationChannel.send(
            NavigationIntent.NavigateTo(
                route = route,
                popUpToRoute = popUpToRoute,
                inclusive = inclusive,
                isSingleTop = isSingleTop,
            )
        )
    }

    override fun tryNavigateTo(
        route: String,
        popUpToRoute: String?,
        inclusive: Boolean,
        isSingleTop: Boolean
    ) {
        navigationChannel.trySend(
            NavigationIntent.NavigateTo(
                route = route,
                popUpToRoute = popUpToRoute,
                inclusive = inclusive,
                isSingleTop = isSingleTop,
            )
        )
    }
}