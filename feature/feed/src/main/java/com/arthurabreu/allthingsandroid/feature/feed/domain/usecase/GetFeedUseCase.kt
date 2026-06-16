package com.arthurabreu.allthingsandroid.feature.feed.domain.usecase

import androidx.paging.PagingData
import com.arthurabreu.allthingsandroid.feature.feed.domain.model.FeedItem
import com.arthurabreu.allthingsandroid.feature.feed.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow

class GetFeedUseCase(private val repository: FeedRepository) {
    operator fun invoke(): Flow<PagingData<FeedItem>> = repository.getFeedStream()
}
