package com.arthurabreu.allthingsandroid.feature.feed.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_items")
data class FeedItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val body: String,
    val imageUrl: String,
    val authorName: String,
)

@Entity(tableName = "feed_remote_keys")
data class FeedRemoteKeyEntity(
    @PrimaryKey val itemId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
)
