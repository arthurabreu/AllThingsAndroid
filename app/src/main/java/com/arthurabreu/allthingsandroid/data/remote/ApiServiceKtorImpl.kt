package com.arthurabreu.allthingsandroid.data.remote

import com.arthurabreu.allthingsandroid.data.config.BaseUrlProvider
import com.arthurabreu.allthingsandroid.data.remote.dto.ApiDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText

class ApiServiceKtorImpl(
    private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider
) : ApiService {
    private var lastRawResponse: String = ""

    override suspend fun fetchData(): ApiDto {
        val response = try {
            client.get {
                url("${baseUrlProvider.baseUrl}/todos/1")
            }
        } catch (e: ClientRequestException) {
            lastRawResponse = e.response.bodyAsText()
            throw e
        } catch (e: ServerResponseException) {
            lastRawResponse = e.response.bodyAsText()
            throw e
        } catch (e: Exception) {
            lastRawResponse = ""  // Clear previous response
            throw e
        }

        lastRawResponse = response.bodyAsText()
        return response.body()
    }

    override suspend fun getRawJsonResponse(): String = lastRawResponse
}