package com.arthurabreu.allthingsandroid.feature.feed.domain.repository

import androidx.paging.PagingData
import com.arthurabreu.allthingsandroid.feature.feed.domain.model.FeedItem
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun getFeedStream(): Flow<PagingData<FeedItem>>
}
