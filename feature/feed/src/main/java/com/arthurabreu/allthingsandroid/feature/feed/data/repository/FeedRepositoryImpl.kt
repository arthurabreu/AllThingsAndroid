package com.arthurabreu.allthingsandroid.feature.feed.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.arthurabreu.allthingsandroid.feature.feed.data.api.FeedApiService
import com.arthurabreu.allthingsandroid.feature.feed.data.db.FeedDatabase
import com.arthurabreu.allthingsandroid.feature.feed.domain.model.FeedItem
import com.arthurabreu.allthingsandroid.feature.feed.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PAGE_SIZE = 20

@OptIn(ExperimentalPagingApi::class)
class FeedRepositoryImpl(
    private val api: FeedApiService,
    private val db: FeedDatabase,
) : FeedRepository {

    override fun getFeedStream(): Flow<PagingData<FeedItem>> =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = FeedRemoteMediator(api, db),
            pagingSourceFactory = { db.feedItemDao().pagingSource() },
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                FeedItem(
                    id = entity.id,
                    title = entity.title,
                    body = entity.body,
                    imageUrl = entity.imageUrl,
                    authorName = entity.authorName,
                )
            }
        }
}
