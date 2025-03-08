package com.arthurabreu.allthingsandroid.domain.usecases

import com.arthurabreu.allthingsandroid.domain.model.DomainData
import com.arthurabreu.allthingsandroid.domain.repos.DataRepository
import kotlinx.coroutines.flow.Flow

class DataUseCases(
    private val repository: DataRepository
) {

    // Insert data
    suspend fun insertData(data: DomainData) {
        repository.saveData(data)
    }

    // For single fetch
    suspend fun getLatestData(): DomainData {
        return repository.getLocalDataOnce()
    }

    // For refresh with remote data
    suspend fun refreshData(): DomainData {
        repository.clearCache()
        val remoteData = repository.getLocalDataOnce()
        repository.updateLastUpdateTime(System.currentTimeMillis())
        return remoteData
    }

    // For continuous observation
    fun observeData(): Flow<DomainData> {
        return repository.getLocalDataStream()
    }
}