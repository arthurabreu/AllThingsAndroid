package com.arthurabreu.allthingsandroid.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun CartLineRow(
    name: String,
    unitPriceLabel: String,
    quantity: Int,
    @DrawableRes iconRes: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    incrementEnabled: Boolean = true,
    avatarContainerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    avatarIconTint: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    testTagPrefix: String = "cart-line",
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag(testTagPrefix),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ProductAvatar(
            iconRes = iconRes,
            contentDescription = name,
            containerColor = avatarContainerColor,
            iconTint = avatarIconTint,
            size = 48.dp,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                text = unitPriceLabel,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        QuantityStepper(
            quantity = quantity,
            onIncrement = onIncrement,
            onDecrement = onDecrement,
            incrementEnabled = incrementEnabled,
            testTagPrefix = "$testTagPrefix-qty",
        )
        IconButton(
            onClick = onRemove,
            modifier = Modifier
                .size(40.dp)
                .testTag("$testTagPrefix-remove"),
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Remove from cart",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
