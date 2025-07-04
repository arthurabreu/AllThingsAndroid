package com.arthurabreu.allthingsandroid.ui.viewmodel.apishowcase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurabreu.allthingsandroid.data.config.Resource
import com.arthurabreu.allthingsandroid.domain.exceptions.DomainException
import com.arthurabreu.allthingsandroid.domain.model.DomainData
import com.arthurabreu.allthingsandroid.domain.model.DomainModel
import com.arthurabreu.allthingsandroid.domain.repos.ApiRepository
import com.arthurabreu.allthingsandroid.domain.usecases.DataUseCases
import com.arthurabreu.allthingsandroid.utils.logger.ClassLogger
import com.arthurabreu.allthingsandroid.utils.logger.logExecution
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ApiShowcaseViewModel(
    private val repository: ApiRepository,
    private val useCases: DataUseCases,
    private val logger: ClassLogger
) : ViewModel() {

    private val _apiData = MutableStateFlow<Resource<DomainModel>>(Resource.Loading)
    val apiData: StateFlow<Resource<DomainModel>> = _apiData

    /* The Db State.
         This is the state that the UI will observe to get the latest data from the database.
         It will be updated by the ViewModel when the data changes.
     */
    private val _dataState = MutableStateFlow<Resource<DomainData>>(Resource.Loading)
    val dataState: StateFlow<Resource<DomainData>> = _dataState

    // For continuous observation
    val observedData: Flow<DomainData> = useCases.observeData() // Observe changes in the data

    init {
        loadApiData() // Load data from api and saves in the db
    }

    private fun loadApiData() {
        viewModelScope.launch {
            logExecution(
                logger = logger,
                tag = "ApiShowcaseViewModel",
                functionName = "loadApiData",
            ){
                _apiData.value = Resource.Loading
                try {
                    val result = repository.getData()
                    _apiData.value = Resource.Success(result)
                } catch (e: DomainException) {
                    _apiData.value = Resource.Error(e)
                    throw e // Re-throw to ensure logExecution captures the failure
                }
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
}