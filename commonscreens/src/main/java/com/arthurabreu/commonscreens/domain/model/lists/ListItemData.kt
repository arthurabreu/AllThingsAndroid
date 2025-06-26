package com.arthurabreu.commonscreens.domain.model.lists

/**
 * Data class representing the data for a single item in the list.
 * @param id A unique identifier for the item.
 * @param text The text content to display for the item.
 */
data class ListItemData(
    val id: String,
    val text: String,
    val description: String? = null
)