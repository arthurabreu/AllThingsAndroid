package com.arthurabreu.allthingsandroid.core.model

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
