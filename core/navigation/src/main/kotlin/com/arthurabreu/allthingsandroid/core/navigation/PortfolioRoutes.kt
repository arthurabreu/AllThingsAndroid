package com.arthurabreu.allthingsandroid.core.navigation

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
    const val LISTS = "listsPaged"
    const val MAPS = "maps"
    const val CHAT = "chat"
    const val VOICE = "voice"
    const val FIREBASE = "firebase"
    const val PERSISTENCE = "persistence"
    const val LEAKS = "leaks"
    const val FEEDBACK = "feedback"

    fun shopDetail(productId: String) = "shop/$productId"
}
