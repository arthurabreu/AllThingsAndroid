package com.arthurabreu.allthingsandroid.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arthurabreu.allthingsandroid.data.local.dao.DataDao
import com.arthurabreu.allthingsandroid.data.local.entity.DataEntity

@Database(
    entities = [DataEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}