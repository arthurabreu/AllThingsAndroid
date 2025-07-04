package com.arthurabreu.allthingsandroid.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arthurabreu.allthingsandroid.data.db.dao.DataDao
import com.arthurabreu.allthingsandroid.data.db.entity.DataEntity

/**
 * Database class for the app
 */
@Database(
    entities = [DataEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}