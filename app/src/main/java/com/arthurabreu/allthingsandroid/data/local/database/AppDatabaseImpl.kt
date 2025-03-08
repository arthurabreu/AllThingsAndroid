package com.arthurabreu.allthingsandroid.data.local.database

import com.arthurabreu.allthingsandroid.data.local.entity.DataEntity
import com.arthurabreu.allthingsandroid.data.mapper.DataMapper
import com.arthurabreu.allthingsandroid.domain.model.DomainData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDatabaseImpl(
    private val database: AppDatabase,
    private val dataMapper: DataMapper
) {

    private val dataDao = database.dataDao()

    suspend fun insertData(data: DataEntity) {
        dataDao.insertData(data)
    }

    suspend fun clearCache() {
        dataDao.clearAll()
    }

    fun getData(): Flow<DataEntity?> {
        return dataDao.getData()
    }

    fun getDataById(id: Int): Flow<DomainData?> {
        return dataDao.getDataById(id).map { entity ->
            entity?.let { dataMapper.dataEntityToDomainData(it) }
        }
    }

    suspend fun deleteDataById(id: Int) {
        dataDao.deleteDataById(id)
    }

    suspend fun updateData(data: DataEntity) {
        dataDao.updateData(data)
    }

    fun countAll(): Flow<Int> {
        return dataDao.countAll()
    }
}