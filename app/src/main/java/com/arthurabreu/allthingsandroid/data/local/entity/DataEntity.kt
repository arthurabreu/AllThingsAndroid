package com.arthurabreu.allthingsandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data entity to represent the data fetched from the API
 */
@Entity(tableName = "data_table")
data class DataEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean,
)