package com.arthurabreu.allthingsandroid.core.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val id: String,
    val body: String,
    @ColumnInfo(name = "pinned", defaultValue = "0") val pinned: Boolean = false,
    @ColumnInfo(name = "headline") val headline: String = "",
    @ColumnInfo(name = "updated_at", defaultValue = "0") val updatedAt: Long = 0L,
)

@Entity(tableName = "cart_lines")
data class CartEntity(
    @PrimaryKey val productId: String,
    val name: String,
    val priceCents: Int,
    val quantity: Int,
    val stock: Int,
)

@Entity(tableName = "feedback")
data class FeedbackEntity(
    @PrimaryKey val id: String,
    val score: Int,
    val comment: String,
    val photoUri: String?,
    val synced: Boolean,
)

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY updated_at DESC")
    fun observe(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(note: NoteEntity)
}

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_lines")
    fun observe(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(line: CartEntity)

    @Query("DELETE FROM cart_lines WHERE productId = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM cart_lines")
    suspend fun clear()
}

@Dao
interface FeedbackDao {
    @Query("SELECT * FROM feedback ORDER BY id DESC")
    fun observe(): Flow<List<FeedbackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: FeedbackEntity)
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE notes ADD COLUMN pinned INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE notes ADD COLUMN updated_at INTEGER NOT NULL DEFAULT 0")
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS notes_new (
                id TEXT NOT NULL,
                body TEXT NOT NULL,
                pinned INTEGER NOT NULL DEFAULT 0,
                headline TEXT NOT NULL DEFAULT '',
                updated_at INTEGER NOT NULL DEFAULT 0,
                PRIMARY KEY(id)
            )
            """.trimIndent(),
        )
        db.execSQL(
            "INSERT INTO notes_new (id, body, pinned, headline, updated_at) " +
                "SELECT id, body, pinned, COALESCE(title, ''), 0 FROM notes",
        )
        db.execSQL("DROP TABLE notes")
        db.execSQL("ALTER TABLE notes_new RENAME TO notes")
    }
}

@Database(
    entities = [NoteEntity::class, CartEntity::class, FeedbackEntity::class],
    version = 3,
    exportSchema = true,
)
abstract class PortfolioDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun cartDao(): CartDao
    abstract fun feedbackDao(): FeedbackDao
}
