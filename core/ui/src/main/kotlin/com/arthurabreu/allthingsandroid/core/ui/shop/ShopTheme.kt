package com.arthurabreu.allthingsandroid.core.ui.shop

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/** Cream / amber palette matching the professional shop layout. */
object ShopColors {
    val Cream = Color(0xFFF5F1E9)
    val CreamSurface = Color(0xFFFAF7F1)
    val Amber = Color(0xFF964B00)
    val AmberDark = Color(0xFF7A3D00)
    val Ink = Color(0xFF2C241B)
    val Muted = Color(0xFF8A7B6B)
    val Divider = Color(0xFFE6DFD4)
    val AvatarFill = Color(0xFFEDE7DC)
    val StepperFill = Color(0xFFF3EEE6)
}

private val ShopColorScheme = lightColorScheme(
    primary = ShopColors.Amber,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE8D4B8),
    onPrimaryContainer = ShopColors.AmberDark,
    secondary = ShopColors.AmberDark,
    onSecondary = Color.White,
    secondaryContainer = ShopColors.AvatarFill,
    onSecondaryContainer = ShopColors.AmberDark,
    tertiary = ShopColors.Amber,
    tertiaryContainer = Color(0xFFE8D4B8),
    onTertiaryContainer = ShopColors.AmberDark,
    background = ShopColors.Cream,
    onBackground = ShopColors.Ink,
    surface = ShopColors.CreamSurface,
    onSurface = ShopColors.Ink,
    surfaceVariant = ShopColors.AvatarFill,
    onSurfaceVariant = ShopColors.Muted,
    outline = ShopColors.Amber,
    outlineVariant = ShopColors.Divider,
    error = Color(0xFFB3261E),
    onError = Color.White,
)

private val ShopShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

@Composable
fun ShopTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ShopColorScheme,
        typography = MaterialTheme.typography,
        shapes = ShopShapes,
        content = content,
    )
}
