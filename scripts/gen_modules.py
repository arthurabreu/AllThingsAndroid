#!/usr/bin/env python3
"""Generate portfolio Gradle modules and Kotlin sources."""
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]

LIB_BUILD = '''plugins {{
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}}

android {{
    namespace = "{namespace}"
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
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
}}
'''

JVM_BUILD = '''plugins {{
    alias(libs.plugins.kotlin.jvm)
}}

java {{
    toolchain {{ languageVersion.set(JavaLanguageVersion.of(21)) }}
}}

tasks.withType<Test> {{ useJUnitPlatform() }}

dependencies {{
    {deps}
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.kotlinx.coroutines.test)
}}
'''

MANIFEST = '''<?xml version="1.0" encoding="utf-8"?>
<manifest />
'''

CONSUMER = "# consumer proguard rules\n"


def write(rel: str, content: str):
    path = ROOT / rel
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content)
    print(rel)


def lib_module(path: str, namespace: str, deps: str = ""):
    write(f"{path}/build.gradle.kts", LIB_BUILD.format(namespace=namespace, deps=deps))
    write(f"{path}/src/main/AndroidManifest.xml", MANIFEST)
    write(f"{path}/consumer-rules.pro", CONSUMER)
    write(f"{path}/proguard-rules.pro", "#\n")


def jvm_module(path: str, deps: str = ""):
    write(f"{path}/build.gradle.kts", JVM_BUILD.format(deps=deps))


# --- core:common (jvm) ---
jvm_module("core/common")
write(
    "core/common/src/main/kotlin/com/arthurabreu/allthingsandroid/core/common/AppResult.kt",
    """package com.arthurabreu.allthingsandroid.core.common

sealed class AppResult<out T> {
    data class Ok<T>(val value: T) : AppResult<T>()
    data class Err(val message: String, val cause: Throwable? = null) : AppResult<Nothing>()

    val isOk: Boolean get() = this is Ok
    fun getOrNull(): T? = (this as? Ok)?.value
}

inline fun <T> runAppCatching(block: () -> T): AppResult<T> =
    try {
        AppResult.Ok(block())
    } catch (t: Throwable) {
        AppResult.Err(t.message ?: "Unknown error", t)
    }
""",
)

write(
    "core/common/src/main/kotlin/com/arthurabreu/allthingsandroid/core/common/AppDispatchers.kt",
    """package com.arthurabreu.allthingsandroid.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class AppDispatchers(
    val io: CoroutineDispatcher = Dispatchers.IO,
    val default: CoroutineDispatcher = Dispatchers.Default,
    val main: CoroutineDispatcher = Dispatchers.Main,
)
""",
)

write(
    "core/common/src/main/kotlin/com/arthurabreu/allthingsandroid/core/common/AppLogger.kt",
    """package com.arthurabreu.allthingsandroid.core.common

interface AppLogger {
    fun d(message: String)
    fun e(message: String, error: Throwable? = null)
}

class NoOpLogger : AppLogger {
    override fun d(message: String) = Unit
    override fun e(message: String, error: Throwable?) = Unit
}
""",
)

write(
    "core/common/src/test/kotlin/com/arthurabreu/allthingsandroid/core/common/AppResultTest.kt",
    """package com.arthurabreu.allthingsandroid.core.common

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AppResultTest {
    @Test
    fun okHoldsValue() {
        val result = AppResult.Ok(2)
        assertTrue(result.isOk)
        assertEquals(2, result.getOrNull())
    }

    @Test
    fun runAppCatchingMapsException() {
        val result = runAppCatching<Int> { error("boom") }
        assertTrue(result is AppResult.Err)
        assertEquals("boom", (result as AppResult.Err).message)
    }
}
""",
)

# --- core:model ---
jvm_module("core/model")
write(
    "core/model/src/main/kotlin/com/arthurabreu/allthingsandroid/core/model/PortfolioModels.kt",
    """package com.arthurabreu.allthingsandroid.core.model

data class CatalogItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val tags: List<String>,
    val route: String,
)

data class CatalogSection(
    val title: String,
    val items: List<CatalogItem>,
)

data class ShopProduct(
    val id: String,
    val name: String,
    val priceCents: Int,
    val stock: Int,
)

data class CartLine(
    val product: ShopProduct,
    val quantity: Int,
) {
    val lineTotalCents: Int get() = product.priceCents * quantity
}

data class ListRow(
    val id: String,
    val title: String,
    val body: String,
)

data class ChatMessage(
    val id: String,
    val author: String,
    val body: String,
    val outgoing: Boolean,
    val pending: Boolean = false,
)

data class FeedbackDraft(
    val id: String,
    val score: Int,
    val comment: String,
    val photoUri: String?,
    val synced: Boolean,
)

data class SchemaInfo(
    val version: Int,
    val description: String,
    val migrationKind: String,
)

data class MapPin(
    val id: String,
    val title: String,
    val lat: Double,
    val lng: Double,
)

data class FirebaseSnapshot(
    val signedIn: Boolean,
    val userLabel: String,
    val remoteBanner: String,
    val fcmToken: String,
    val notes: List<String>,
    val lastUpload: String?,
)
""",
)

write(
    "core/model/src/test/kotlin/com/arthurabreu/allthingsandroid/core/model/CartLineTest.kt",
    """package com.arthurabreu.allthingsandroid.core.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CartLineTest {
    @Test
    fun lineTotalMultipliesPrice() {
        val product = ShopProduct("1", "Beer", 500, 10)
        assertEquals(1500, CartLine(product, 3).lineTotalCents)
    }
}
""",
)

# --- core:domain ---
jvm_module("core/domain", deps="implementation(project(\":core:common\"))\n    implementation(project(\":core:model\"))")
write(
    "core/domain/src/main/kotlin/com/arthurabreu/allthingsandroid/core/domain/Catalog.kt",
    """package com.arthurabreu.allthingsandroid.core.domain

import com.arthurabreu.allthingsandroid.core.model.CatalogItem
import com.arthurabreu.allthingsandroid.core.model.CatalogSection

object PortfolioCatalog {
    fun sections(): List<CatalogSection> = listOf(
        CatalogSection(
            "Products",
            listOf(
                item("shop", "Shop + cart", "Catalog, detail, offline cart", listOf("Room", "Koin"), "shop"),
                item("lists", "Paged lists", "Paging, empty, error, retry", listOf("Paging", "UDF"), "lists"),
            ),
        ),
        CatalogSection(
            "Location",
            listOf(item("maps", "Maps", "Pins, location, polyline", listOf("Maps", "Ktor"), "maps")),
        ),
        CatalogSection(
            "Realtime",
            listOf(
                item("chat", "Chat", "WebSocket + offline queue", listOf("Ktor", "WS"), "chat"),
                item("voice", "Voice AI", "Audio stream to Python server", listOf("Media3", "WS"), "voice"),
            ),
        ),
        CatalogSection(
            "Backend",
            listOf(
                item(
                    "firebase",
                    "Firebase suite",
                    "Auth, Firestore, RC, FCM, Crashlytics, Storage",
                    listOf("Firebase"),
                    "firebase",
                ),
            ),
        ),
        CatalogSection(
            "Quality",
            listOf(
                item("persistence", "Room + migrations", "v1 to v3 with schema in git", listOf("Room"), "persistence"),
                item("leaks", "LeakCanary", "Intentional leak behind a flag", listOf("LeakCanary"), "leaks"),
            ),
        ),
        CatalogSection(
            "Field",
            listOf(item("feedback", "Field feedback", "CSAT + photo + offline sync", listOf("CameraX", "WM"), "feedback")),
        ),
        CatalogSection(
            "Lab / Design System",
            listOf(
                item("lab-buttons", "Buttons", "Design system buttons", listOf("Lab"), "buttons"),
                item("lab-lists", "List styles", "Compose list gallery", listOf("Lab"), "listsLab"),
                item("lab-textfields", "Text fields", "Input gallery", listOf("Lab"), "textFields"),
                item("lab-login", "Login", "Auth UI samples", listOf("Lab"), "logins"),
                item("lab-login-fake", "Login fake", "Alternate login", listOf("Lab"), "loginFake"),
                item("lab-api", "API showcase", "JSONPlaceholder + Ktor", listOf("Lab", "Ktor"), "jsonPlaceHolder"),
                item("lab-meditation", "Meditation UI", "Stylized layout", listOf("Lab"), "meditation"),
                item("lab-calculator", "Calculator", "UI + instrumented tests", listOf("Lab"), "calculator"),
                item("lab-solid", "SOLID", "Principles playground", listOf("Lab"), "solid"),
                item("lab-design", "Design principles", "UI heuristics", listOf("Lab"), "designPrinciple"),
                item("lab-olympics", "Olympics", "Themed list", listOf("Lab"), "olympics"),
                item("lab-profile", "Profile", "Args navigation", listOf("Lab"), "profile"),
                item("lab-settings", "Settings", "App settings", listOf("Lab"), "settings"),
                item("lab-download", "Download", "File download demo", listOf("Lab"), "download"),
            ),
        ),
    )

    private fun item(
        id: String,
        title: String,
        subtitle: String,
        tags: List<String>,
        route: String,
    ) = CatalogItem(id, title, subtitle, tags, route)
}
""",
)

write(
    "core/domain/src/main/kotlin/com/arthurabreu/allthingsandroid/core/domain/ShopCart.kt",
    """package com.arthurabreu.allthingsandroid.core.domain

import com.arthurabreu.allthingsandroid.core.common.AppResult
import com.arthurabreu.allthingsandroid.core.model.CartLine
import com.arthurabreu.allthingsandroid.core.model.ShopProduct

class ShopCatalog {
    fun products(): List<ShopProduct> = listOf(
        ShopProduct("sku-lager", "Lager 350ml", 499, 24),
        ShopProduct("sku-ipa", "IPA 473ml", 799, 12),
        ShopProduct("sku-stout", "Stout 330ml", 899, 6),
        ShopProduct("sku-pack", "Mixed 6-pack", 2999, 3),
    )

    fun byId(id: String): ShopProduct? = products().find { it.id == id }
}

class CartCalculator {
    fun add(lines: List<CartLine>, product: ShopProduct): AppResult<List<CartLine>> {
        val existing = lines.find { it.product.id == product.id }
        val nextQty = (existing?.quantity ?: 0) + 1
        if (nextQty > product.stock) return AppResult.Err("Out of stock")
        val updated = if (existing == null) {
            lines + CartLine(product, 1)
        } else {
            lines.map { if (it.product.id == product.id) it.copy(quantity = nextQty) else it }
        }
        return AppResult.Ok(updated)
    }

    fun remove(lines: List<CartLine>, productId: String): List<CartLine> =
        lines.mapNotNull { line ->
            when {
                line.product.id != productId -> line
                line.quantity <= 1 -> null
                else -> line.copy(quantity = line.quantity - 1)
            }
        }

    fun totalCents(lines: List<CartLine>): Int = lines.sumOf { it.lineTotalCents }
}
""",
)

write(
    "core/domain/src/main/kotlin/com/arthurabreu/allthingsandroid/core/domain/ListFilter.kt",
    """package com.arthurabreu.allthingsandroid.core.domain

import com.arthurabreu.allthingsandroid.core.model.ListRow

class ListFilter {
    fun apply(rows: List<ListRow>, query: String): List<ListRow> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return rows
        return rows.filter { it.title.lowercase().contains(q) || it.body.lowercase().contains(q) }
    }

    fun page(rows: List<ListRow>, page: Int, pageSize: Int = 20): List<ListRow> {
        val from = page.coerceAtLeast(0) * pageSize
        if (from >= rows.size) return emptyList()
        return rows.subList(from, minOf(from + pageSize, rows.size))
    }
}

object SeedRows {
    fun generate(count: Int = 80): List<ListRow> =
        (1..count).map { i ->
            ListRow(
                id = "row-$i",
                title = "Ticket #$i",
                body = if (i % 7 == 0) "Blocked on sync" else "Ready for review",
            )
        }
}
""",
)

write(
    "core/domain/src/main/kotlin/com/arthurabreu/allthingsandroid/core/domain/FeedbackRules.kt",
    """package com.arthurabreu.allthingsandroid.core.domain

import com.arthurabreu.allthingsandroid.core.common.AppResult
import com.arthurabreu.allthingsandroid.core.model.FeedbackDraft

class FeedbackRules {
    fun validate(score: Int, comment: String): AppResult<Unit> {
        if (score !in 1..5) return AppResult.Err("Score must be 1 to 5")
        if (comment.isBlank() && score <= 2) return AppResult.Err("Low scores need a comment")
        return AppResult.Ok(Unit)
    }

    fun pending(drafts: List<FeedbackDraft>): List<FeedbackDraft> = drafts.filterNot { it.synced }
}
""",
)

write(
    "core/domain/src/main/kotlin/com/arthurabreu/allthingsandroid/core/domain/SchemaStory.kt",
    """package com.arthurabreu.allthingsandroid.core.domain

import com.arthurabreu.allthingsandroid.core.model.SchemaInfo

object SchemaStory {
    fun versions(): List<SchemaInfo> = listOf(
        SchemaInfo(1, "notes table", "baseline"),
        SchemaInfo(2, "notes.pinned column", "AutoMigration"),
        SchemaInfo(3, "notes.updated_at + rename title", "manual Migration"),
    )
}
""",
)

write(
    "core/domain/src/test/kotlin/com/arthurabreu/allthingsandroid/core/domain/DomainTest.kt",
    """package com.arthurabreu.allthingsandroid.core.domain

import com.arthurabreu.allthingsandroid.core.common.AppResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DomainTest {
    private val cart = CartCalculator()
    private val catalog = ShopCatalog()
    private val filter = ListFilter()
    private val feedback = FeedbackRules()

    @Test
    fun catalogHasFourProducts() {
        assertEquals(4, catalog.products().size)
    }

    @Test
    fun addRespectsStock() {
        val product = catalog.byId("sku-pack")!!
        var lines = emptyList<com.arthurabreu.allthingsandroid.core.model.CartLine>()
        repeat(3) {
            lines = (cart.add(lines, product) as AppResult.Ok).value
        }
        val overflow = cart.add(lines, product)
        assertTrue(overflow is AppResult.Err)
        assertEquals(8997, cart.totalCents(lines))
    }

    @Test
    fun removeDropsLineAtZero() {
        val product = catalog.byId("sku-lager")!!
        val added = (cart.add(emptyList(), product) as AppResult.Ok).value
        assertTrue(cart.remove(added, product.id).isEmpty())
    }

    @Test
    fun listFilterAndPaging() {
        val rows = SeedRows.generate(25)
        assertEquals(4, filter.apply(rows, "blocked").size)
        assertEquals(20, filter.page(rows, 0).size)
        assertEquals(5, filter.page(rows, 1).size)
        assertTrue(filter.page(rows, 4).isEmpty())
    }

    @Test
    fun feedbackRequiresCommentOnLowScore() {
        assertTrue(feedback.validate(2, "") is AppResult.Err)
        assertTrue(feedback.validate(5, "") is AppResult.Ok)
        assertTrue(feedback.validate(9, "x") is AppResult.Err)
    }

    @Test
    fun catalogSectionsIncludeLabAtEnd() {
        val sections = PortfolioCatalog.sections()
        assertEquals("Lab / Design System", sections.last().title)
        assertTrue(sections.first().items.any { it.route == "shop" })
    }

    @Test
    fun schemaStoryHasThreeVersions() {
        assertEquals(3, SchemaStory.versions().size)
        assertEquals("manual Migration", SchemaStory.versions().last().migrationKind)
    }
}
""",
)

print("core jvm modules written")
