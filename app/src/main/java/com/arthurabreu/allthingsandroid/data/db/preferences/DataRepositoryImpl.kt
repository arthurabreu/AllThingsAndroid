package com.arthurabreu.allthingsandroid.data.db.preferences

import com.arthurabreu.allthingsandroid.data.db.database.AppDatabaseImpl
import com.arthurabreu.allthingsandroid.data.mapper.DataMapper
import com.arthurabreu.allthingsandroid.domain.model.DomainData
import com.arthurabreu.allthingsandroid.domain.repos.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/*
    * DataRepositoryImpl is the implementation of the DataRepository interface.
    * It is responsible for handling the data flow between the local database and the view model.
    * It uses the AppDatabaseImpl, DataMapper and PreferencesManager to interact with the database and preferences.
    * It also uses the DomainData model to represent the data.
    * It implements the functions defined in the DataRepository interface.
    * It uses the Flow class to handle continuous observation of the data.
    * It uses the suspend keyword to handle asynchronous operations.
    * It uses the first() function to get the first element of a flow.
    * It uses the map() function to transform the data from one type to another.
    * It uses the let() function to execute a block of code if the object is not null.
    * It uses the ?: operator to provide a default value if the object is null.
    * It uses the updateLastUpdateTime() function to update the last update time in the preferences.
    * It uses the clearCache() function to clear the cache in the database.
    * It uses the deleteDataById() function to delete data by id in the database.
    * It uses the updateData() function to update data in the database.
    * It uses the saveData() function to save data in the database.
    * It uses the getLastUpdateTime() function to get the last update time from the preferences.
    * It uses the getLocalDataOnce() function to get the local data once from the database.
    * It uses the getLocalDataStream() function to get the local data stream from the database.
    * It uses the getDataById() function to get data by id from the database.
    * It uses the DomainData model to represent the data.
    * It uses the DataEntity model to represent the data.
    * It uses the DataMapper to map data between the domain and entity layers.
    * It uses the PreferencesManager to handle preferences operations.
    * It uses the AppDatabaseImpl to interact with the local database.
 */
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
