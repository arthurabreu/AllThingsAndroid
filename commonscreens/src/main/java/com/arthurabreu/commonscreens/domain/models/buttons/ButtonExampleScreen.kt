package com.arthurabreu.commonscreens.domain.models.buttons

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.ui.unit.dp
import com.arthurabreu.commonscreens.domain.data.AllButtonStates

/*
    * Example of how to create and configure button states for use in a Composable.
 */
@Composable
fun createButtonStates(): AllButtonStates {

    // Example 1: Using MaterialTheme colors (Recommended for theming)
    val primaryButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), // Default disabled alpha
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)   // Default disabled alpha
    )

    // Example 2: Using custom specific colors
    val customButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFF0088FF), // A custom blue
        contentColor = Color.White,
        disabledContainerColor = Color.Gray,
        disabledContentColor = Color.LightGray
    )

    // Now, create your AllButtonStates instance
    return AllButtonStates(
        text = "Submit Action",
        onClick = { /* TODO: Define action */ },
        enabled = true,
        loading = false,
        icon = Icons.AutoMirrored.Filled.Send,
        iconDescription = "Send Icon",
        colors = primaryButtonColors, // Use the colors you defined
        // Or use customButtonColors:
        // colors = customButtonColors,
        elevation = 2.dp,
        modifier = Modifier
    )
}

// In your UI where you use the button:
@Composable
fun ButtonExampleScreen() {
    val buttonState = createButtonStates()
    ButtonComposable(state = buttonState)
}