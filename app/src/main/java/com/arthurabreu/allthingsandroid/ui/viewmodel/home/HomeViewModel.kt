package com.arthurabreu.allthingsandroid.ui.viewmodel.home

import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ApiShowcaseFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ButtonsFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.CalculatorFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.DesignPrincipleFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.DownloadFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ListsFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.LoginFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.MeditationFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.OlympicsFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.PortfolioFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ProfileFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.SettingsFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.SolidFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.TextFieldsFeature
import com.arthurabreu.allthingsandroid.utils.logger.ClassLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val appNavigator: AppNavigator,
    private val logger: ClassLogger,
) : ViewModel() {

    private val _userdata = MutableStateFlow("user123")
    val userdata: StateFlow<String?> = _userdata

    fun open(route: String) {
        logger.d("open $route")
        when (route) {
            "shop" -> appNavigator.tryNavigateTo(PortfolioFeature.Shop.route)
            "listsPaged" -> appNavigator.tryNavigateTo(PortfolioFeature.Lists.route)
            "maps" -> appNavigator.tryNavigateTo(PortfolioFeature.Maps.route)
            "chat" -> appNavigator.tryNavigateTo(PortfolioFeature.Chat.route)
            "voice" -> appNavigator.tryNavigateTo(PortfolioFeature.Voice.route)
            "firebase" -> appNavigator.tryNavigateTo(PortfolioFeature.Firebase.route)
            "persistence" -> appNavigator.tryNavigateTo(PortfolioFeature.Persistence.route)
            "leaks" -> appNavigator.tryNavigateTo(PortfolioFeature.Leaks.route)
            "feedback" -> appNavigator.tryNavigateTo(PortfolioFeature.Feedback.route)
            "buttons" -> onButtonsClick()
            "listsLab" -> onListsClick()
            "textFields" -> onTextFieldsClick()
            "logins" -> onLoginsClick()
            "loginFake" -> onLoginFakeClick()
            "jsonPlaceHolder" -> onApiShowcaseClick()
            "meditation" -> onMeditationUiClick()
            "calculator" -> onCalculatorUiClick()
            "solid" -> onSolidUiClick()
            "designPrinciple" -> onDesignPrincipleUiClick()
            "olympics" -> onOlympicsUiClick()
            "profile" -> onProfileClick(_userdata.value)
            "settings" -> onSettingsClick()
            "download" -> onDownloadClick()
            else -> logger.d("unknown route $route")
        }
    }

    fun onProfileClick(userId: String) {
        appNavigator.tryNavigateTo(ProfileFeature.Profile(userId = userId))
    }

    fun onSettingsClick() {
        appNavigator.tryNavigateTo(SettingsFeature.Settings.route)
    }

    fun onDownloadClick() {
        appNavigator.tryNavigateTo(DownloadFeature.Download.route)
    }

    fun onButtonsClick() {
        appNavigator.tryNavigateTo(ButtonsFeature.Buttons.route)
    }

    fun onListsClick() {
        appNavigator.tryNavigateTo(ListsFeature.Lists.route)
    }

    fun onLoginsClick() {
        appNavigator.tryNavigateTo(LoginFeature.Logins.route)
    }

    fun onLoginFakeClick() {
        appNavigator.tryNavigateTo(LoginFeature.LoginFake.route)
    }

    fun onTextFieldsClick() {
        appNavigator.tryNavigateTo(TextFieldsFeature.TextFields.route)
    }

    fun onApiShowcaseClick() {
        appNavigator.tryNavigateTo(ApiShowcaseFeature.JsonPlaceHolder.route)
    }

    fun onMeditationUiClick() {
        appNavigator.tryNavigateTo(MeditationFeature.Meditation.route)
    }

    fun onCalculatorUiClick() {
        appNavigator.tryNavigateTo(CalculatorFeature.Calculator.route)
    }

    fun onSolidUiClick() {
        appNavigator.tryNavigateTo(SolidFeature.Solid.route)
    }

    fun onDesignPrincipleUiClick() {
        appNavigator.tryNavigateTo(DesignPrincipleFeature.DesignPrinciple.route)
    }

    fun onOlympicsUiClick() {
        appNavigator.tryNavigateTo(OlympicsFeature.OlympicsFeature.route)
    }
}
