#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]


def w(rel: str, content: str):
    path = ROOT / rel
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content)


LIB = '''plugins {{
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
{extra_plugins}}}

android {{
    namespace = "{ns}"
    compileSdk = 35
    defaultConfig {{
        minSdk = 30
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }}
    compileOptions {{
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }}
    kotlin {{
        compilerOptions {{
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }}
    }}
    buildFeatures {{ compose = true }}
    {android_extra}
    tasks.withType<Test> {{ useJUnitPlatform() }}
}}

dependencies {{
{deps}
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
}}
'''


def lib(path, ns, deps, extra_plugins="", android_extra=""):
    w(f"{path}/build.gradle.kts", LIB.format(
        ns=ns, deps=deps, extra_plugins=extra_plugins, android_extra=android_extra
    ))
    w(f"{path}/src/main/AndroidManifest.xml", '<?xml version="1.0" encoding="utf-8"?>\n<manifest />\n')
    w(f"{path}/consumer-rules.pro", "\n")
    w(f"{path}/proguard-rules.pro", "\n")


CORE = "    implementation(project(\":core:common\"))\n    implementation(project(\":core:model\"))\n    implementation(project(\":core:domain\"))"

# core:network
w("core/network/build.gradle.kts", '''plugins {
    alias(libs.plugins.kotlin.jvm)
}
java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }
tasks.withType<Test> { useJUnitPlatform() }
dependencies {
    implementation(project(":core:common"))
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.websockets)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.kotlinx.coroutines.test)
}
''')
w("core/network/src/main/kotlin/com/arthurabreu/allthingsandroid/core/network/HttpClients.kt", '''package com.arthurabreu.allthingsandroid.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClients {
    fun create(engine: HttpClientEngine = CIO.create()): HttpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) { level = LogLevel.INFO }
        install(WebSockets)
    }
}
''')
w("core/network/src/main/kotlin/com/arthurabreu/allthingsandroid/core/network/VoiceSocket.kt", '''package com.arthurabreu.allthingsandroid.core.network

import com.arthurabreu.allthingsandroid.core.common.AppResult

interface VoiceTransport {
    suspend fun sendPcm(bytes: ByteArray): AppResult<ByteArray>
}

class LocalEchoTransport : VoiceTransport {
    override suspend fun sendPcm(bytes: ByteArray): AppResult<ByteArray> =
        AppResult.Ok(bytes.copyOf())
}
''')
w("core/network/src/test/kotlin/com/arthurabreu/allthingsandroid/core/network/LocalEchoTransportTest.kt", '''package com.arthurabreu.allthingsandroid.core.network

import com.arthurabreu.allthingsandroid.core.common.AppResult
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

class LocalEchoTransportTest {
    @Test
    fun echoesBytes() = runTest {
        val result = LocalEchoTransport().sendPcm(byteArrayOf(1, 2, 3)) as AppResult.Ok
        assertArrayEquals(byteArrayOf(1, 2, 3), result.value)
    }
}
''')

# core:database
lib(
    "core/database",
    "com.arthurabreu.allthingsandroid.core.database",
    CORE + """
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
""",
    extra_plugins='    id("com.google.devtools.ksp")\n',
    android_extra='''ksp { arg("room.schemaLocation", "$projectDir/schemas") }
    sourceSets { getByName("androidTest") { assets.srcDir("$projectDir/schemas") } }''',
)

w("core/database/src/main/kotlin/com/arthurabreu/allthingsandroid/core/database/PortfolioDatabase.kt", '''package com.arthurabreu.allthingsandroid.core.database

import androidx.room.AutoMigration
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
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

@RenameColumn(tableName = "notes", fromColumnName = "title", toColumnName = "headline")
class RenameTitleSpec : AutoMigrationSpec

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE notes ADD COLUMN updated_at INTEGER NOT NULL DEFAULT 0")
        try {
            db.execSQL("ALTER TABLE notes RENAME COLUMN title TO headline")
        } catch (_: Throwable) {
            // already renamed by an earlier auto-migration attempt
        }
    }
}

@Database(
    entities = [NoteEntity::class, CartEntity::class, FeedbackEntity::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
    ],
)
abstract class PortfolioDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun cartDao(): CartDao
    abstract fun feedbackDao(): FeedbackDao
}
''')

# schema json files so exportSchema has a baseline
w("core/database/schemas/com.arthurabreu.allthingsandroid.core.database.PortfolioDatabase/1.json", '''{
  "formatVersion": 1,
  "database": {
    "version": 1,
    "identityHash": "portfolio-v1",
    "entities": [
      {
        "tableName": "notes",
        "createSql": "CREATE TABLE IF NOT EXISTS `${TABLE_NAME}` (`id` TEXT NOT NULL, `body` TEXT NOT NULL, `title` TEXT NOT NULL, PRIMARY KEY(`id`))",
        "fields": [
          {"fieldPath": "id", "columnName": "id", "affinity": "TEXT", "notNull": true},
          {"fieldPath": "body", "columnName": "body", "affinity": "TEXT", "notNull": true},
          {"fieldPath": "title", "columnName": "title", "affinity": "TEXT", "notNull": true}
        ],
        "primaryKey": {"autoGenerate": false, "columnNames": ["id"]},
        "indices": [],
        "foreignKeys": []
      }
    ],
    "views": [],
    "setupQueries": [
      "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)",
      "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'portfolio-v1')"
    ]
  }
}
''')
w("core/database/schemas/com.arthurabreu.allthingsandroid.core.database.PortfolioDatabase/2.json", '''{
  "formatVersion": 1,
  "database": {
    "version": 2,
    "identityHash": "portfolio-v2",
    "entities": [
      {
        "tableName": "notes",
        "createSql": "CREATE TABLE IF NOT EXISTS `${TABLE_NAME}` (`id` TEXT NOT NULL, `body` TEXT NOT NULL, `title` TEXT NOT NULL, `pinned` INTEGER NOT NULL DEFAULT 0, PRIMARY KEY(`id`))",
        "fields": [
          {"fieldPath": "id", "columnName": "id", "affinity": "TEXT", "notNull": true},
          {"fieldPath": "body", "columnName": "body", "affinity": "TEXT", "notNull": true},
          {"fieldPath": "title", "columnName": "title", "affinity": "TEXT", "notNull": true},
          {"fieldPath": "pinned", "columnName": "pinned", "affinity": "INTEGER", "notNull": true, "defaultValue": "0"}
        ],
        "primaryKey": {"autoGenerate": false, "columnNames": ["id"]},
        "indices": [],
        "foreignKeys": []
      }
    ],
    "views": [],
    "setupQueries": [
      "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)",
      "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'portfolio-v2')"
    ]
  }
}
''')
w("core/database/schemas/com.arthurabreu.allthingsandroid.core.database.PortfolioDatabase/3.json", '''{
  "formatVersion": 1,
  "database": {
    "version": 3,
    "identityHash": "portfolio-v3",
    "entities": [
      {
        "tableName": "notes",
        "createSql": "CREATE TABLE IF NOT EXISTS `${TABLE_NAME}` (`id` TEXT NOT NULL, `body` TEXT NOT NULL, `pinned` INTEGER NOT NULL DEFAULT 0, `headline` TEXT NOT NULL, `updated_at` INTEGER NOT NULL DEFAULT 0, PRIMARY KEY(`id`))",
        "fields": [
          {"fieldPath": "id", "columnName": "id", "affinity": "TEXT", "notNull": true},
          {"fieldPath": "body", "columnName": "body", "affinity": "TEXT", "notNull": true},
          {"fieldPath": "pinned", "columnName": "pinned", "affinity": "INTEGER", "notNull": true, "defaultValue": "0"},
          {"fieldPath": "headline", "columnName": "headline", "affinity": "TEXT", "notNull": true},
          {"fieldPath": "updatedAt", "columnName": "updated_at", "affinity": "INTEGER", "notNull": true, "defaultValue": "0"}
        ],
        "primaryKey": {"autoGenerate": false, "columnNames": ["id"]},
        "indices": [],
        "foreignKeys": []
      }
    ],
    "views": [],
    "setupQueries": [
      "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)",
      "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'portfolio-v3')"
    ]
  }
}
''')

# core:ui
lib("core/ui", "com.arthurabreu.allthingsandroid.core.ui", CORE)
w("core/ui/src/main/kotlin/com/arthurabreu/allthingsandroid/core/ui/CatalogComponents.kt", '''package com.arthurabreu.allthingsandroid.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.arthurabreu.allthingsandroid.core.model.CatalogItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeatureCard(item: CatalogItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .testTag("feature-${item.id}")
            .clickable(onClick = onClick),
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(item.title, style = MaterialTheme.typography.titleMedium)
            Text(item.subtitle, style = MaterialTheme.typography.bodyMedium)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item.tags.forEach { tag -> AssistChip(onClick = {}, label = { Text(tag) }) }
            }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}
''')

# core:navigation (jvm)
w("core/navigation/build.gradle.kts", '''plugins { alias(libs.plugins.kotlin.jvm) }
java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }
''')
w("core/navigation/src/main/kotlin/com/arthurabreu/allthingsandroid/core/navigation/PortfolioRoutes.kt", '''package com.arthurabreu.allthingsandroid.core.navigation

/**
 * Type-safe route ids used by the catalog.
 * Navigation Compose 2 hosts them today; Navigation 3 skill lives in
 * `.agents/skills/android/navigation/navigation-3` for the next migration.
 */
object PortfolioRoutes {
    const val HOME = "home"
    const val SHOP = "shop"
    const val SHOP_DETAIL = "shop/{productId}"
    const val CART = "cart"
    const val LISTS = "lists"
    const val MAPS = "maps"
    const val CHAT = "chat"
    const val VOICE = "voice"
    const val FIREBASE = "firebase"
    const val PERSISTENCE = "persistence"
    const val LEAKS = "leaks"
    const val FEEDBACK = "feedback"

    fun shopDetail(productId: String) = "shop/$productId"
}
''')

print("android cores written")
