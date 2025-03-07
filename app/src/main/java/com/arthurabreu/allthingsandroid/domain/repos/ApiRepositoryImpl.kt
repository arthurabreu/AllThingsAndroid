package com.arthurabreu.allthingsandroid.domain.repos

import android.util.Log
import com.arthurabreu.allthingsandroid.data.error.ErrorHandler
import com.arthurabreu.allthingsandroid.data.mapper.ApiMapper
import com.arthurabreu.allthingsandroid.data.remote.ApiService
import com.arthurabreu.allthingsandroid.domain.model.DomainModel

class ApiRepositoryImpl(
    private val service: ApiService,
    private val mapper: ApiMapper,
    private val errorHandler: ErrorHandler
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

            mapper.mapToDomain(apiResponse)
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