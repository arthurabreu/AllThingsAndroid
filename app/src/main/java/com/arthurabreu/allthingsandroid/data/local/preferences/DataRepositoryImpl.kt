package com.arthurabreu.allthingsandroid.data.local.preferences

import com.arthurabreu.allthingsandroid.data.local.database.AppDatabaseImpl
import com.arthurabreu.allthingsandroid.data.mapper.DataMapper
import com.arthurabreu.allthingsandroid.domain.model.DomainData
import com.arthurabreu.allthingsandroid.domain.repos.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataRepositoryImpl(
    private val appDatabaseImpl: AppDatabaseImpl,
    private val dataMapper: DataMapper,
    private val preferencesManager: PreferencesManager
) : DataRepository {

    // For continuous observation
    override fun getLocalDataStream(): Flow<DomainData> {
        return appDatabaseImpl.getData().map { dataEntity ->
            dataEntity?.let { dataMapper.dataEntityToDomainData(it) }
                ?: DomainData(0, 0, "", true)
        }
    }

    // For single fetch
    override suspend fun getLocalDataOnce(): DomainData {
        val dataEntity = appDatabaseImpl.getData().first() // DataEntity. First() is used to get the first element of the flow
        val domainData =
            dataEntity?.let { dataMapper.dataEntityToDomainData(it) } // DomainData. For each DataEntity, map it to DomainData
        return domainData ?: DomainData(0,0,"", true) // Return the list of DomainData (List<DomainData>)
    }

    override suspend fun getDataById(id: Int): Flow<DomainData?> {
        return appDatabaseImpl.getDataById(id)
    }

    override suspend fun deleteDataById(id: Int) {
        appDatabaseImpl.deleteDataById(id)
    }

    override suspend fun updateData(data: DomainData) {
        appDatabaseImpl.updateData(dataMapper.domainDataToEntity(data))
    }

    override suspend fun clearCache() {
        appDatabaseImpl.clearCache()
    }

    override suspend fun saveData(data: DomainData) {
        appDatabaseImpl.insertData(dataMapper.domainDataToEntity(data) )
    }

    override suspend fun getLastUpdateTime(): Long {
        return preferencesManager.lastUpdateTime.first()
    }

    override suspend fun updateLastUpdateTime(time: Long) {
        preferencesManager.updateLastUpdateTime(time)
    }
}
