package com.arthurabreu.commonscreens.ui.state.lists

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arthurabreu.commonscreens.domain.model.lists.ListItemData
import com.arthurabreu.commonscreens.ui.style.ListDividerStyle
import com.arthurabreu.commonscreens.ui.style.ListItemStyle

/**
 * The state class that holds all configuration for the [com.arthurabreu.commonscreens.ui.composables.lists.ListsComposable].
 * @param items The list of [ListItemData] to display.
 * @param listBackgroundColor The background color of the entire list.
 * @param listPadding The padding for the entire list container.
 * @param itemStyle The style to apply to each item in the list.
 * @param dividerStyle The style for dividers between items.
 */
data class AllListsState(
    val items: List<ListItemData>, // can have anything inside, but must be a ListItemData
    val listTitle: String? = null,
    val titlePadding: PaddingValues = PaddingValues(0.dp),
    val listBackgroundColor: Color = Color.Companion.Transparent,
    val listPadding: PaddingValues = PaddingValues(8.dp),
    val itemStyle: ListItemStyle = ListItemStyle(),
    val dividerStyle: ListDividerStyle = ListDividerStyle()
)