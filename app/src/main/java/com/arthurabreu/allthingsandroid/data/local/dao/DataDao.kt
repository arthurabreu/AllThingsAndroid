package com.arthurabreu.allthingsandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.arthurabreu.allthingsandroid.data.local.entity.DataEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the data_table.
 * This is where you define your database operations.
 * You can use the @Query annotation to define custom queries.
 * You can use the @Insert annotation to insert data.
 * You can use the @Update annotation to update data.
 * You can use the @Delete annotation to delete data.
 * You can use the @Transaction annotation to run multiple operations in a single transaction.
 * You can use the @RawQuery annotation to run raw SQL queries.
 * You can use the @DatabaseView annotation to create a view.
 * You can use the @Relation annotation to define relationships between entities.
 * You can use the @Embedded annotation to embed an entity inside another entity.
 * You can use the @Ignore annotation to ignore a field.
 * You can use the @ColumnInfo annotation to define column properties.
 * You can use the @PrimaryKey annotation to define a primary key.
 * You can use the @ForeignKey annotation to define foreign keys.
 * You can use the @Index annotation to define indexes.
 * You can use the @TypeConverter annotation to define type converters.
 * You can use the @Database annotation to define a database.
 * You can use the @Entity annotation to define an entity.
 * You can use the @Fts4 annotation to define a FTS entity.
 * You can use the @Fts3 annotation to define a FTS entity.
 * You can use the @View annotation to define a view.
 * You can use the @NonNull annotation to define a non-null field.
 */
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