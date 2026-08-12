package com.arthurabreu.allthingsandroid.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun QuantityStepper(
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    incrementEnabled: Boolean = true,
    decrementEnabled: Boolean = quantity > 0,
    testTagPrefix: String = "qty",
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        OutlinedIconButton(
            onClick = onDecrement,
            enabled = decrementEnabled,
            modifier = Modifier
                .size(36.dp)
                .testTag("$testTagPrefix-dec"),
        ) {
            Icon(Icons.Filled.Remove, contentDescription = "Decrease quantity")
        }
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(28.dp)
                .testTag("$testTagPrefix-value"),
        )
        FilledIconButton(
            onClick = onIncrement,
            enabled = incrementEnabled,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier
                .size(36.dp)
                .testTag("$testTagPrefix-inc"),
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Increase quantity")
        }
    }
}
