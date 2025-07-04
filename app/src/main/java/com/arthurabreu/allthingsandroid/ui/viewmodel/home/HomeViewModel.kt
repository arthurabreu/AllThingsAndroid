package com.arthurabreu.allthingsandroid.ui.viewmodel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ApiShowcaseFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ButtonsFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.DownloadFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ListsFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.LoginFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ProfileFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.SettingsFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.TextFieldsFeature
import com.arthurabreu.allthingsandroid.data.config.Resource
import com.arthurabreu.allthingsandroid.domain.exceptions.DomainException
import com.arthurabreu.allthingsandroid.domain.model.DomainData
import com.arthurabreu.allthingsandroid.domain.model.DomainModel
import com.arthurabreu.allthingsandroid.domain.repos.ApiRepository
import com.arthurabreu.allthingsandroid.domain.usecases.DataUseCases
import com.arthurabreu.allthingsandroid.utils.logger.ClassLogger
import com.arthurabreu.allthingsandroid.utils.logger.logApiExecution
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel(
    private val appNavigator: AppNavigator,
    private val logger: ClassLogger
) : ViewModel() {

    private val _userdata = MutableStateFlow("")
    val userdata: StateFlow<String?> = _userdata

    init {
        _userdata.value = "user123" // Data to pass through arguments to other destinations
        Log.d("MainViewModel", "MainViewModel created")

        performCalculation(2,3)
    }

    fun onProfileClick(userId: String) {
        val route = ProfileFeature.Profile(userId = userId)
        appNavigator.tryNavigateTo(route)
    }

    fun onSettingsClick() {
        appNavigator.tryNavigateTo(SettingsFeature.Settings.route)
    }

    fun onDownloadClick() {
        appNavigator.tryNavigateTo(DownloadFeature.Download.route)
    }

    fun onButtonsClick () {
        appNavigator.tryNavigateTo(ButtonsFeature.Buttons.route)
    }

    fun onListsClick () {
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

    // Shows that the logger can be used to log the execution of a suspend function
    /*
    *
    * 2025-04-07 11:24:17.929  9537-9537  HomeViewModel           com.arthurabreu.allthingsandroid     D  Execution Started:
   CallerResolver.getCallerInfo()
-> HomeViewModel$loadApiData$1.invokeSuspend()          <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
   BaseContinuationImpl.resumeWith()
   Logger$DefaultImpls.d$default()
-> HomeViewModel$loadApiData$1.invokeSuspend()          <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
   BaseContinuationImpl.resumeWith()
2025-04-07 11:24:17.952  9537-9537  HomeViewModel           com.arthurabreu.allthingsandroid     D  Execution Started:
   CallerResolver.getCallerInfo()
-> HomeViewModel$performCalculation$1.invokeSuspend()          <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
   BaseContinuationImpl.resumeWith()
   Logger$DefaultImpls.d$default()
-> HomeViewModel$performCalculation$1.invokeSuspend()          <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
   BaseContinuationImpl.resumeWith()
2025-04-07 11:24:17.955  9537-9537  HomeViewModel           com.arthurabreu.allthingsandroid     D  Execution Completed:
   CallerResolver.getCallerInfo()
-> HomeViewModel$performCalculation$1.invokeSuspend()          <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
   BaseContinuationImpl.resumeWith()
   Logger$DefaultImpls.d$default()
-> HomeViewModel$performCalculation$1.invokeSuspend()
   BaseContinuationImpl.resumeWith()
2025-04-07 11:24:18.565  9537-9537  HomeViewModel           com.arthurabreu.allthingsandroid     D  Execution Completed:
   CallerResolver.getCallerInfo()
-> HomeViewModel$loadApiData$1.invokeSuspend()          <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
   BaseContinuationImpl.resumeWith()
   Logger$DefaultImpls.d$default()
-> HomeViewModel$loadApiData$1.invokeSuspend()          <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
   BaseContinuationImpl.resumeWith()
     */
    private fun performCalculation(a: Int, b: Int): Int {
        // To call a suspend function, we need a coroutine scope
        return runBlocking {
            logApiExecution(logger) {
                println("Performing calculation...")
                return@logApiExecution a + b // The block returns the result
            }
        }
    }
}