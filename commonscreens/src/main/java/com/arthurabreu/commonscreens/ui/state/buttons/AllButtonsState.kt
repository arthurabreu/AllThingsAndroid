package com.arthurabreu.commonscreens.ui.state.buttons

import androidx.compose.material3.ButtonColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AllButtonsState(
    val text: String,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
    val loading: Boolean = false,
    val iconLeft: ImageVector? = null,
    val iconRight: ImageVector? = null,
    val iconDescription: String? = null,
    val colors: ButtonColors,
    val elevation: Dp = 2.dp,
    val modifier: Modifier = Modifier.Companion
)