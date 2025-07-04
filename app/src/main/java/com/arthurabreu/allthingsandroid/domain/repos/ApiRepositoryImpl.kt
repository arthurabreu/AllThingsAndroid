package com.arthurabreu.allthingsandroid.domain.repos

import android.util.Log
import com.arthurabreu.allthingsandroid.data.error.ErrorHandler
import com.arthurabreu.allthingsandroid.data.mapper.ApiMapper
import com.arthurabreu.allthingsandroid.data.mapper.DataMapper
import com.arthurabreu.allthingsandroid.data.remote.api.ApiService
import com.arthurabreu.allthingsandroid.domain.model.DomainModel
import com.arthurabreu.allthingsandroid.domain.usecases.DataUseCases

/**
 * Repository implementation that fetches data from the API
 * and maps it to the DomainModel
 * @param service the service that fetches the data
 * @param mapper the mapper that maps the DTO to the DomainModel
 * @param dataMapper the mapper that maps the DomainModel to the DomainData
 * @param errorHandler the error handler that handles exceptions
 * @param dataUseCases the use cases that interact with the local database
 * @see ApiRepository
 * @see ApiService
 * @see ApiMapper
 * @see DataMapper
 * @see ErrorHandler
 * @see DataUseCases
 */
class ApiRepositoryImpl(
    private val service: ApiService,
    private val mapper: ApiMapper,
    private val dataMapper: DataMapper,
    private val errorHandler: ErrorHandler,
    private val dataUseCases: DataUseCases
) : ApiRepository {

    override suspend fun getData(): DomainModel {
        Log.d("Repository", "Fetching data...")
        return try {
            val apiResponse = service.fetchData()
            val rawJson = service.getRawJsonResponse()
            Log.d("Repository", """
                Successful response:
                DTO: $apiResponse
                Raw JSON: $rawJson
            """.trimIndent())

            val mappedModel = mapper.mapToDomainModel(apiResponse) // Maps to DomainModel, which is the app's model
            val domainData = dataMapper.domainModelToDomainData(mappedModel) // Maps to DomainData, which is the local db model
            dataUseCases.insertData(domainData) // Saves api data on local db for Room & Cache example

            mappedModel // returns DomainModel for ui stuff
        } catch (e: Exception) {
            val rawJson = service.getRawJsonResponse()
            Log.e("Repository", """
                Error: ${e.javaClass.simpleName}
                Message: ${e.message}
                Raw JSON: ${rawJson.ifEmpty { "N/A" }}
            """.trimIndent(), e)

            throw errorHandler.handleError(e)
        }
    }
}