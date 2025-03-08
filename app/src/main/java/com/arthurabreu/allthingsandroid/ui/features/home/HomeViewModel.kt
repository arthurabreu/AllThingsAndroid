package com.arthurabreu.allthingsandroid.ui.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ProfileFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.SettingsFeature
import com.arthurabreu.allthingsandroid.data.config.Resource
import com.arthurabreu.allthingsandroid.domain.exceptions.DomainException
import com.arthurabreu.allthingsandroid.domain.model.DomainData
import com.arthurabreu.allthingsandroid.domain.model.DomainModel
import com.arthurabreu.allthingsandroid.domain.repos.ApiRepository
import com.arthurabreu.allthingsandroid.domain.usecases.DataUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val appNavigator: AppNavigator,
    private val repository: ApiRepository,
    private val useCases: DataUseCases
) : ViewModel() {

    private val _userdata = MutableStateFlow("")
    val userdata: StateFlow<String?> = _userdata

    private val _apiData = MutableStateFlow<Resource<DomainModel>>(Resource.Loading)
    val apiData: StateFlow<Resource<DomainModel>> = _apiData

    /* The Db State.
         This is the state that the UI will observe to get the latest data from the database.
         It will be updated by the ViewModel when the data changes.
     */
    private val _dataState = MutableStateFlow<Resource<DomainData>>(Resource.Loading)
    val dataState: StateFlow<Resource<DomainData>> = _dataState

    init {
        _userdata.value = "user123" // Data to pass through arguments to other destinations
        Log.d("MainViewModel", "MainViewModel created")


        loadApiData() // Load data from api and saves in the db
    }

    fun onProfileClick() {
        when (val currentData = _apiData.value) {
            is Resource.Success -> {
                val userId = currentData.data.userId.toString() // Assuming userId in DomainModel
                val route = ProfileFeature.Profile(userId = userId)
                appNavigator.tryNavigateTo(route)
            }
            is Resource.Error -> {
                // Handle error state
                //showError(currentData.exception.message)
            }
            Resource.Loading -> {
                // Handle loading state
                //showLoading()
            }
        }
    }

    fun onSettingsClick() {
        appNavigator.tryNavigateTo(SettingsFeature.Settings.route)
    }

    private fun loadApiData() {
        viewModelScope.launch {
            _apiData.value = Resource.Loading
            try {
                val result = repository.getData()
                _apiData.value = Resource.Success(result)
            } catch (e: DomainException) {
                _apiData.value = Resource.Error(e)
            }
        }
    }

   /* Persistence Operations
      Room db, DataStore operations
      (For single fetch)
   */
   private fun getLatestData() {
       viewModelScope.launch {
           _dataState.value = Resource.Loading
           try {
               val data = useCases.getLatestData()
               _dataState.value = Resource.Success(data)
           } catch (e: Exception) {
               _dataState.value = Resource.Error(DomainException.UnknownError(e.message ?: "Unknown error"))
           }
       }
   }

    // For continuous observation
    val observedData: Flow<DomainData> = useCases.observeData() // Observe changes in the data
}

/*
Usages:
// Single fetch
viewModel.loadData()

// Observe changes
viewModel.observedData
    .onEach { data ->
        // Update UI with latest data
    }
    .launchIn(viewModelScope)
 */