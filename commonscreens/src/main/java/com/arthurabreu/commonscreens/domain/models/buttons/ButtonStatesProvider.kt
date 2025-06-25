package com.arthurabreu.commonscreens.domain.models.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arthurabreu.allthingsandroid.commonscreens.R
import com.arthurabreu.commonscreens.domain.data.AllButtonsState

object ButtonStatesProvider {
    @Composable
    fun getAllButtonStates(): List<AllButtonsState> = listOf(
        // Submit Button with default colors
        buttonWithDefaultColors(),
        // Submit Button with custom colors
        buttonWithCustomColors(),
        // Standard button with MaterialTheme colors
        AllButtonsState(
            text = stringResource(R.string.exit_to_app),
            onClick = { },
            iconRight = Icons.AutoMirrored.Filled.ExitToApp,
            iconDescription = stringResource(R.string.exit_to_app),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onTertiary,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = 2.dp,
            modifier = Modifier
        ),
        // Button with send icon
        AllButtonsState(
            text = stringResource(R.string.send),
            onClick = { },
            iconRight = Icons.AutoMirrored.Filled.Send,
            iconDescription = stringResource(R.string.send),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = 2.dp,
            modifier = Modifier
        ),
        // Button with rounded borders
        AllButtonsState(
            text = stringResource(R.string.favorite),
            onClick = { },
            iconRight = Icons.Filled.Favorite,
            iconDescription = stringResource(R.string.favorite),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE91E63),
                contentColor = Color.White
            ),
            elevation = 2.dp,
            // Add .clip(RoundedCornerShape(50)) in the Composable if you want rounded borders
            modifier = Modifier
                .clip(RoundedCornerShape(50))
        ),
        // Button with square borders
        AllButtonsState(
            text = stringResource(R.string.like),
            onClick = { },
            iconRight = Icons.Filled.ThumbUp,
            iconDescription = stringResource(R.string.like),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            ),
            elevation = 0.dp,
            // Add .clip(RoundedCornerShape(0.dp)) in the Composable if you want square borders
            modifier = Modifier
                .clip(RoundedCornerShape(0.dp))
        ),
        // Text Button Only
        AllButtonsState(
            text = stringResource(R.string.just_text),
            onClick = { },
            iconRight = null,
            iconDescription = stringResource(R.string.just_text),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            ),
            elevation = 2.dp,
            modifier = Modifier
        ),
        // Loading Button
        AllButtonsState(
            text = stringResource(R.string.loading),
            onClick = { },
            iconRight = Icons.Filled.Build, // Changed to a more generic loading icon
            iconDescription = stringResource(R.string.loading),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            ),
            elevation = 2.dp,
            loading = true,
            modifier = Modifier
        ),
        // Disabled Button
        AllButtonsState(
            text = stringResource(R.string.deactivated),
            onClick = { },
            iconRight = null,
            iconDescription = stringResource(R.string.deactivated),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.DarkGray,
                disabledContainerColor = Color.DarkGray.copy(alpha = 0.5f),
                disabledContentColor = Color.White.copy(alpha = 0.5f)
            ),
            elevation = 2.dp,
            enabled = false,
            modifier = Modifier
        ),
        // Secondary Button
        AllButtonsState(
            text = stringResource(R.string.secondary),
            onClick = { },
            iconRight = Icons.Filled.AccountBox,
            iconDescription = stringResource(R.string.secondary),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            elevation = 2.dp,
            modifier = Modifier
        ),
        // Dark shade Button
        AllButtonsState(
            text = stringResource(R.string.dark_tones),
            onClick = { },
            iconRight = Icons.Filled.ThumbUp,
            iconDescription = stringResource(R.string.dark_tones),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            elevation = 4.dp,
            modifier = Modifier
        ),
        // Light shade Button
        AllButtonsState(
            text = stringResource(R.string.clear_tones),
            onClick = { },
            iconRight = Icons.Filled.ArrowDropDown,
            iconDescription = stringResource(R.string.clear_tones),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = 0.dp,
            modifier = Modifier
        ),
        // Button with elevation
        AllButtonsState(
            text = stringResource(R.string.with_elevation),
            onClick = { },
            iconRight = Icons.Filled.Call,
            iconDescription = stringResource(R.string.with_elevation),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6200EE),
                contentColor = Color.White
            ),
            elevation = 8.dp,
            modifier = Modifier
        ),
        // Button without elevation
        AllButtonsState(
            text = stringResource(R.string.without_elevation),
            onClick = { },
            iconRight = Icons.Filled.AccountCircle,
            iconDescription = stringResource(R.string.without_elevation),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF03DAC5),
                contentColor = Color.Black
            ),
            elevation = 0.dp,
            modifier = Modifier
        ),
        // Button with icon on the left
        AllButtonsState(
            text = stringResource(R.string.icon_on_the_left), // Make sure this string resource exists
            onClick = { },
            iconLeft = Icons.Filled.Face,
            iconDescription = stringResource(R.string.icon_on_the_left),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF9800), // Orange
                contentColor = Color.White
            ),
            elevation = 2.dp,
            modifier = Modifier
        ),
        // Button with icon on the right
        AllButtonsState(
            text = stringResource(R.string.icon_on_the_right), // Make sure this string resource exists
            onClick = { },
            iconRight = Icons.Filled.MoreVert,
            iconDescription = stringResource(R.string.icon_on_the_right),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF009688), // Teal
                contentColor = Color.White
            ),
            elevation = 2.dp,
            modifier = Modifier
        ),
        // Button with different sizes - Big
        AllButtonsState(
            text = stringResource(R.string.big), // Make sure this string resource exists
            onClick = { },
            iconRight = Icons.Filled.Notifications,
            iconDescription = stringResource(R.string.big),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8BC34A), // Light Green
                contentColor = Color.Black
            ),
            elevation = 2.dp,
            modifier = Modifier.size(200.dp, 56.dp)
        ),
        // Button with different sizes - Small
        AllButtonsState(
            text = stringResource(R.string.small), // Make sure this string resource exists
            onClick = { },
            iconRight = null,
            iconDescription = stringResource(R.string.small),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFCDDC39), // Lime
                contentColor = Color.Black
            ),
            elevation = 2.dp,
            modifier = Modifier.size(100.dp, 36.dp)
        ),
        // Disabled Button with Icon
        AllButtonsState(
            text = stringResource(R.string.disabled), // Reusing existing, consider a more specific one
            onClick = { },
            iconRight = Icons.Filled.Delete,
            iconDescription = stringResource(R.string.disabled),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray.copy(alpha = 0.6f), // Slightly more opaque
                disabledContentColor = Color.White.copy(alpha = 0.6f)
            ),
            elevation = 2.dp,
            enabled = false,
            modifier = Modifier
        ),
        // Button with blue border and text
        AllButtonsState(
            text = stringResource(R.string.blue_border), // Make sure this string resource exists
            onClick = { },
            iconRight = null,
            iconDescription = stringResource(R.string.blue_border),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color(0xFF2196F3) // Blue
            ),
            elevation = 0.dp, // Usually outlined buttons don't have elevation unless specified
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(width = 2.dp, color = Color(0xFF2196F3), shape = RoundedCornerShape(8.dp))
        ),
        // Button with rounded corners
        AllButtonsState(
            text = stringResource(R.string.rounded_corners), // Make sure this string resource exists
            onClick = {  },
            iconRight = Icons.Filled.Done,
            iconDescription = stringResource(R.string.rounded_corners),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50), // Green
                contentColor = Color.White
            ),
            elevation = 2.dp,
            modifier = Modifier
                .clip(RoundedCornerShape(64.dp))
        ),
        // Button with shadow
        AllButtonsState(
            text = stringResource(R.string.with_shadow), // Make sure this string resource exists
            onClick = { },
            iconRight = null,
            iconDescription = stringResource(R.string.with_shadow),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF607D8B), // Blue Grey
                contentColor = Color.White
            ),
            elevation = 0.dp, // Elevation is handled by the shadow modifier
            modifier = Modifier
                .shadow(12.dp, RoundedCornerShape(8.dp))
        ),
        // Button without icon
        AllButtonsState(
            text = stringResource(R.string.iconless_button), // Make sure this string resource exists
            onClick = { },
            iconRight = null,
            iconDescription = stringResource(R.string.iconless_button),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00BCD4), // Cyan
                contentColor = Color.White
            ),
            elevation = 2.dp,
            modifier = Modifier
        ),
        // Button with icon and long text
        AllButtonsState(
            text = stringResource(R.string.icon_and_long_text_button), // Make sure this string resource exists
            onClick = { },
            iconRight = Icons.Filled.ShoppingCart,
            iconDescription = stringResource(R.string.icon_and_long_text_button),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3F51B5), // Indigo
                contentColor = Color.White
            ),
            elevation = 2.dp,
            modifier = Modifier
        ),
        // Warning Button
        AllButtonsState(
            text = stringResource(R.string.warning_button), 
            onClick = { },
            iconLeft = Icons.Filled.Warning,
            iconDescription = stringResource(R.string.warning_button_desc),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFC107),
                contentColor = Color.Black
            ),
            elevation = 2.dp,
            modifier = Modifier
        ),
        // Success Button
        AllButtonsState(
            text = stringResource(R.string.success_button),
            onClick = { },
            iconLeft = Icons.Filled.CheckCircle,
            iconDescription = stringResource(R.string.success_button_desc),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            ),
            elevation = 2.dp,
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
        ),
        // Info Button
        AllButtonsState(
            text = stringResource(R.string.info_button),
            onClick = { },
            iconLeft = Icons.Filled.Info,
            iconDescription = stringResource(R.string.info_button_desc),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF03A9F4),
                contentColor = Color.White
            ),
            elevation = 1.dp,
            modifier = Modifier
        ),
        // Danger/Error Button
        AllButtonsState(
            text = stringResource(R.string.danger_button),
            onClick = { },
            iconLeft = Icons.Filled.Close,
            iconDescription = stringResource(R.string.danger_button_desc),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF44336),
                contentColor = Color.White
            ),
            elevation = 2.dp,
            modifier = Modifier
        ),
        // Outline Button - Dark
        AllButtonsState(
            text = stringResource(R.string.outline_dark_button),
            onClick = { },
            iconRight = Icons.Filled.Edit,
            iconDescription = stringResource(R.string.outline_dark_button_desc),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            elevation = 0.dp,
            modifier = Modifier
                .border(width = 1.dp, color = Color(0xFF212121), shape = RoundedCornerShape(4.dp))
        ),
        // Ghost Button (Text Only with custom color)
        AllButtonsState(
            text = stringResource(R.string.ghost_button_purple),
            onClick = { },
            iconRight = null,
            iconDescription = stringResource(R.string.ghost_button_purple_desc),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color(0xFF673AB7)
            ),
            elevation = 0.dp,
            modifier = Modifier
        ),
        // Button with subtle icon color difference
        AllButtonsState(
            text = stringResource(R.string.subtle_icon_button),
            onClick = { },
            iconRight = Icons.Filled.Share,
            // For icon color to be different, you'd typically handle it inside the Button's content lambda
            // by tinting the Icon composable directly. ButtonColors primarily controls overall contentColor.
            // This example shows how to set a general content color.
            iconDescription = stringResource(R.string.subtle_icon_button_desc),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF795548), // Brown
                contentColor = Color.White // Text and Icon will be white
            ),
            elevation = 2.dp,
            modifier = Modifier
        ),
        // Refresh Button - Often an Icon Button, but can be a full button
        AllButtonsState(
            text = stringResource(R.string.refresh_button),
            onClick = { },
            iconLeft = Icons.Filled.Refresh,
            iconDescription = stringResource(R.string.refresh_button_desc),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00ACC1),
                contentColor = Color.White
            ),
            elevation = 2.dp,
            modifier = Modifier
        ),
        // Search Button - Distinct style
        AllButtonsState(
            text = stringResource(R.string.search_action_button),
            onClick = { },
            iconLeft = Icons.Filled.Search,
            iconDescription = stringResource(R.string.search_action_button_desc),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5C6BC0),
                contentColor = Color.White
            ),
            elevation = 3.dp,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        )
    )
}

@Composable
fun buttonWithDefaultColors(): AllButtonsState {

    // Example 1: Using MaterialTheme colors (Recommended for theming)
    val primaryButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), // Default disabled alpha
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)   // Default disabled alpha
    )

    return AllButtonsState(
        text = "Submit Action",
        onClick = { /* TODO: Define action */ },
        enabled = true,
        loading = false,
        iconRight = Icons.AutoMirrored.Filled.Send,
        iconDescription = "Send Icon",
        colors = primaryButtonColors,
        elevation = 2.dp,
        modifier = Modifier
    )
}

@Composable
fun buttonWithCustomColors(): AllButtonsState {
    // Example 2: Using custom specific colors
    val customButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFF0088FF), // A custom blue
        contentColor = Color.White,
        disabledContainerColor = Color.Gray,
        disabledContentColor = Color.LightGray
    )

    return AllButtonsState(
        text = "Custom Action",
        onClick = { /* TODO: Define action */ },
        enabled = true,
        loading = false,
        iconRight = Icons.AutoMirrored.Filled.Send,
        iconDescription = "Send Icon",
        colors = customButtonColors,
        elevation = 4.dp,
        modifier = Modifier
    )
}