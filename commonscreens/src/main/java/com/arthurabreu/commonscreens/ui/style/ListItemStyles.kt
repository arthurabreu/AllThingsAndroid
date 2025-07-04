package com.arthurabreu.commonscreens.ui.style

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Data class for styling individual list items.
 * @param textColor The color of the text.
 * @param backgroundColor The background color of the item.
 * @param padding The padding around the item content.
 * @param border The border stroke for the item.
 * @param shape The shape of the item's background and border.
 */
data class ListItemStyle(
    val textColor: Color = Color.Black,
    val backgroundColor: Color = Color.White,
    val padding: PaddingValues = PaddingValues(16.dp),
    val border: BorderStroke? = null,
    val shape: Shape = RoundedCornerShape(0.dp)
)

/**
 * Data class for styling dividers between list items.
 * @param showAbove Whether to show a divider above the item.
 * @param showBelow Whether to show a divider below the item.
 * @param color The color of the divider.
 * @param thickness The thickness of the divider.
 * @param padding The horizontal padding for the divider.
 */
data class ListDividerStyle(
    val showAbove: Boolean = false,
    val showBelow: Boolean = true,
    val color: Color = Color.LightGray,
    val thickness: Dp = 1.dp,
    val padding: PaddingValues = PaddingValues(horizontal = 16.dp)
)