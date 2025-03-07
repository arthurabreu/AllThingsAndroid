package com.arthurabreu.allthingsandroid.data.remote

import android.util.Log
import com.arthurabreu.allthingsandroid.data.remote.dto.ApiDto
import com.arthurabreu.allthingsandroid.data.remote.dto.ResponseDto
import com.arthurabreu.allthingsandroid.domain.exception.NetworkException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerializationException

class KtorApiServiceImpl(private val client: HttpClient) : ApiService {
    override suspend fun fetchData(): ApiDto {
        try {
            val response: HttpResponse = client.get {
                url("https://jsonplaceholder.typicode.com/todos/1")
            }
            // Log raw JSON response
            val rawJson = response.bodyAsText() // OK
            Log.d("KtorApiService", "Raw JSON: $rawJson") // OK
            return response.body()
        } catch (e: Exception) {
            // Use a default code for connection errors
            throw NetworkException.ConnectionError(code = e.message.toString())
        } catch (e: SerializationException) {
            throw NetworkException.SerializationError(code = e.message.toString())
        } catch (e: SocketTimeoutException) {
            throw NetworkException.TimeoutError(code = e.message.toString())
        }
    }

    override suspend fun sendData(data: ApiDto): ResponseDto {
        TODO("Not yet implemented")
    }
}