package com.arthurabreu.allthingsandroid.core.domain

import com.arthurabreu.allthingsandroid.core.model.CatalogItem
import com.arthurabreu.allthingsandroid.core.model.CatalogSection

object PortfolioCatalog {
    fun sections(): List<CatalogSection> = listOf(
        CatalogSection(
            "Products",
            listOf(
                item("shop", "Shop + cart", "Catalog, detail, offline cart", listOf("Room", "Koin"), "shop"),
                item("lists", "Paged lists", "Paging, empty, error, retry", listOf("Paging", "UDF"), "listsPaged"),
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
