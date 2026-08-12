package com.arthurabreu.allthingsandroid.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun ProductListItem(
    name: String,
    priceLabel: String,
    @DrawableRes iconRes: Int,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    stockLabel: String? = null,
    addEnabled: Boolean = true,
    addLabel: String = "Add",
    addTestTag: String? = null,
    avatarContainerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    avatarIconTint: Color = MaterialTheme.colorScheme.onSecondaryContainer,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ProductAvatar(
            iconRes = iconRes,
            contentDescription = name,
            containerColor = avatarContainerColor,
            iconTint = avatarIconTint,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(name, style = MaterialTheme.typography.titleMedium)
            Text(
                priceLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            if (stockLabel != null) {
                Text(
                    stockLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        FilledTonalButton(
            onClick = onAddClick,
            enabled = addEnabled,
            modifier = if (addTestTag != null) Modifier.testTag(addTestTag) else Modifier,
        ) {
            Text(addLabel)
        }
    }
}
