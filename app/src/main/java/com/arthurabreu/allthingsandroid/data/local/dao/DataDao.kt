package com.arthurabreu.allthingsandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.arthurabreu.allthingsandroid.data.local.entity.DataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DataDao {
    @Query("SELECT * FROM data_table")
    fun getData(): Flow<DataEntity?> // Changed to nullable to handle empty table

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: DataEntity)

    @Query("DELETE FROM data_table")
    suspend fun clearAll()

    // Add your custom functions here:
    // Example: get data by ID
    @Query("SELECT * FROM data_table WHERE id = :id")
    fun getDataById(id: Int): Flow<DataEntity?>

    // Example: delete data by ID
    @Query("DELETE FROM data_table WHERE id = :id")
    suspend fun deleteDataById(id: Int)

    // Example: update data by ID
    @Update
    suspend fun updateData(data: DataEntity)

    // Example: count all rows
    @Query("SELECT COUNT(*) FROM data_table")
    fun countAll(): Flow<Int>
}