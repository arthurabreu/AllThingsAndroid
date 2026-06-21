package com.arthurabreu.allthingsandroid.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arthurabreu.allthingsandroid.core.designsystem.theme.AllThingsAndroidTheme
import com.arthurabreu.allthingsandroid.core.designsystem.theme.AppTheme

@Composable
fun AppListItemCard(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    val cardContent: @Composable () -> Unit = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (leadingContent != null) {
                leadingContent()
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = AppTheme.spacing.small),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.extraSmall),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            if (trailingContent != null) {
                trailingContent()
            }
        }
    }

    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            ),
        ) {
            cardContent()
        }
    } else {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            ),
        ) {
            cardContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppListItemCardPreview() {
    AllThingsAndroidTheme {
        AppListItemCard(
            title = "Item Title",
            subtitle = "Item Subtitle",
            modifier = Modifier.padding(AppTheme.spacing.medium),
        )
    }
}
