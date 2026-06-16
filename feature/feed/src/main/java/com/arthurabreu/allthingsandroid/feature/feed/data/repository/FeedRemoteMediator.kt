package com.arthurabreu.allthingsandroid.feature.feed.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.arthurabreu.allthingsandroid.feature.feed.data.api.FeedApiService
import com.arthurabreu.allthingsandroid.feature.feed.data.db.FeedDatabase
import com.arthurabreu.allthingsandroid.feature.feed.data.db.FeedItemEntity
import com.arthurabreu.allthingsandroid.feature.feed.data.db.FeedRemoteKeyEntity

private const val PAGE_SIZE = 20
private const val STARTING_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator(
    private val api: FeedApiService,
    private val db: FeedDatabase,
) : RemoteMediator<Int, FeedItemEntity>() {

    private val itemDao = db.feedItemDao()
    private val keyDao = db.feedRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FeedItemEntity>,
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> STARTING_PAGE
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    keyDao.remoteKeyById(lastItem.id)?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val posts = api.getPosts(page = page, pageSize = PAGE_SIZE)
            val photoOffset = (page - 1) * PAGE_SIZE
            val photos = api.getPhotos(start = photoOffset, limit = PAGE_SIZE)
            val photoMap = photos.associateBy { it.id % PAGE_SIZE }

            val endReached = posts.isEmpty()
            val nextPage = if (endReached) null else page + 1

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    itemDao.clearAll()
                    keyDao.clearAll()
                }
                val entities = posts.mapIndexed { index, post ->
                    val photo = photoMap[index]
                    FeedItemEntity(
                        id = post.id,
                        title = post.title,
                        body = post.body,
                        imageUrl = photo?.thumbnailUrl ?: "",
                        authorName = "User ${post.userId}",
                    )
                }
                val keys = entities.map { FeedRemoteKeyEntity(it.id, prevKey = if (page == STARTING_PAGE) null else page - 1, nextKey = nextPage) }
                itemDao.insertAll(entities)
                keyDao.insertAll(keys)
            }
            MediatorResult.Success(endOfPaginationReached = endReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
