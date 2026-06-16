package com.arthurabreu.allthingsandroid.feature.feed.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FeedItemDao {
    @Query("SELECT * FROM feed_items")
    fun pagingSource(): PagingSource<Int, FeedItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<FeedItemEntity>)

    @Query("DELETE FROM feed_items")
    suspend fun clearAll()
}

@Dao
interface FeedRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<FeedRemoteKeyEntity>)

    @Query("SELECT * FROM feed_remote_keys WHERE itemId = :id")
    suspend fun remoteKeyById(id: Int): FeedRemoteKeyEntity?

    @Query("DELETE FROM feed_remote_keys")
    suspend fun clearAll()
}
