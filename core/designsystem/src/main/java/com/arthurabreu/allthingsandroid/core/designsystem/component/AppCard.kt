package com.arthurabreu.allthingsandroid.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
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
fun AppCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            ),
            content = content,
        )
    } else {
        Card(
            modifier = modifier,
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            ),
            content = content,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppCardPreview() {
    AllThingsAndroidTheme {
        AppCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Card content",
                modifier = Modifier.padding(AppTheme.spacing.medium),
            )
        }
    }
}
