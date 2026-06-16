package com.arthurabreu.allthingsandroid.feature.feed.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class FeedApiService(private val client: HttpClient) {

    suspend fun getPosts(page: Int, pageSize: Int): List<PostDto> =
        client.get("https://jsonplaceholder.typicode.com/posts") {
            parameter("_page", page)
            parameter("_limit", pageSize)
        }.body()

    suspend fun getPhotos(start: Int, limit: Int): List<PhotoDto> =
        client.get("https://jsonplaceholder.typicode.com/photos") {
            parameter("_start", start)
            parameter("_limit", limit)
        }.body()
}
