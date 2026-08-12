package com.arthurabreu.allthingsandroid.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun CartBadgeIconButton(
    itemCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String = "Open cart",
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.testTag("cart-open"),
    ) {
        BadgedBox(
            badge = {
                if (itemCount > 0) {
                    Badge { Text(itemCount.toString()) }
                }
            },
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = contentDescription,
            )
        }
    }
}
