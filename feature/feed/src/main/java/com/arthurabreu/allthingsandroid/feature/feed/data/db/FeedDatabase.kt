package com.arthurabreu.allthingsandroid.feature.feed.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FeedItemEntity::class, FeedRemoteKeyEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class FeedDatabase : RoomDatabase() {
    abstract fun feedItemDao(): FeedItemDao
    abstract fun feedRemoteKeyDao(): FeedRemoteKeyDao
}
