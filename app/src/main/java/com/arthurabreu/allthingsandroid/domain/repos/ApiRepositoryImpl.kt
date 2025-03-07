package com.arthurabreu.allthingsandroid.domain.repos

import android.util.Log
import com.arthurabreu.allthingsandroid.data.mapper.ApiMapper
import com.arthurabreu.allthingsandroid.data.remote.ApiService
import com.arthurabreu.allthingsandroid.domain.exception.NetworkException
import com.arthurabreu.allthingsandroid.domain.model.DomainModel
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

class ApiRepositoryImpl(
    private val service: ApiService,
    private val mapper: ApiMapper
) : ApiRepository {

    override suspend fun getData(): DomainModel {
        Log.d("Repository", "Fetching data...")
        return try {
            val data = service.fetchData()
            Log.d("Repository", "Data received: $data")
            mapper.mapToDomain(data)
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching data", e)
            when (e) {
                is ClientRequestException -> throw NetworkException.ServerError(e.message)
                is ServerResponseException -> throw NetworkException.ServerError(e.message)
                is IOException -> throw NetworkException.ConnectionError(e.message.toString())
                is SerializationException -> throw NetworkException.SerializationError(e.message.toString())
                else -> throw NetworkException.UnknownError(e.message.toString())
            }
        }
    }

//    override suspend fun postData(data: DomainModel): Result<Unit> {
//        TODO("Not yet implemented")
//    }
}