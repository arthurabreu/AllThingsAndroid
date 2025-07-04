package com.arthurabreu.allthingsandroid.domain.repos

import com.arthurabreu.allthingsandroid.domain.model.DomainData
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getLocalDataStream(): Flow<DomainData> // For continuous observation
    suspend fun getLocalDataOnce(): DomainData // For single fetch
    suspend fun saveData(data: DomainData)
    suspend fun clearCache()
    suspend fun getLastUpdateTime(): Long
    suspend fun updateLastUpdateTime(time: Long)
    suspend fun getDataById(id: Int): Flow<DomainData?>
    suspend fun deleteDataById(id: Int)
    suspend fun updateData(data: DomainData)
}