package com.arthurabreu.allthingsandroid.ui.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ProfileFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.SettingsFeature
import com.arthurabreu.allthingsandroid.data.config.Resource
import com.arthurabreu.allthingsandroid.domain.exceptions.DomainException
import com.arthurabreu.allthingsandroid.domain.model.DomainModel
import com.arthurabreu.allthingsandroid.domain.repos.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val appNavigator: AppNavigator,
    private val repository: ApiRepository
) : ViewModel() {

    private val _userdata = MutableStateFlow("")
    val userdata: StateFlow<String?> = _userdata

    private val _data = MutableStateFlow<Resource<DomainModel>>(Resource.Loading)
    val data: StateFlow<Resource<DomainModel>> = _data


    init {
        _userdata.value = "user123"
        Log.d("MainViewModel", "MainViewModel created")
        loadData()
    }

    fun onProfileClick() {
        when (val currentData = _data.value) {
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

    private fun loadData() {
        viewModelScope.launch {
            _data.value = Resource.Loading
            try {
                val result = repository.getData()
                _data.value = Resource.Success(result)
            } catch (e: DomainException) {
                _data.value = Resource.Error(e)
            }
        }
    }

}