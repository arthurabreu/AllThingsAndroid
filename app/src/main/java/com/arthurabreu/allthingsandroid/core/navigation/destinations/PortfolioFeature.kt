package com.arthurabreu.allthingsandroid.core.navigation.destinations

object PortfolioFeature {
    data object Shop : NoParamsDestination("shop")
    data object Lists : NoParamsDestination("listsPaged")
    data object Maps : NoParamsDestination("maps")
    data object Chat : NoParamsDestination("chat")
    data object Voice : NoParamsDestination("voice")
    data object Firebase : NoParamsDestination("firebase")
    data object Persistence : NoParamsDestination("persistence")
    data object Leaks : NoParamsDestination("leaks")
    data object Feedback : NoParamsDestination("feedback")
}
