package com.arthurabreu.allthingsandroid.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProductAvatar(
    @DrawableRes iconRes: Int,
    contentDescription: String?,
    containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    iconTint: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    size: Dp = 56.dp,
    shape: Shape = RoundedCornerShape(12.dp),
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(containerColor),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = Modifier.size(size * 0.45f),
        )
    }
}

/** Circular variant kept for callers that still want the previous look. */
@Composable
fun ProductAvatarCircle(
    @DrawableRes iconRes: Int,
    contentDescription: String?,
    containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    iconTint: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    size: Dp = 48.dp,
    modifier: Modifier = Modifier,
) {
    ProductAvatar(
        iconRes = iconRes,
        contentDescription = contentDescription,
        containerColor = containerColor,
        iconTint = iconTint,
        size = size,
        shape = CircleShape,
        modifier = modifier,
    )
}
