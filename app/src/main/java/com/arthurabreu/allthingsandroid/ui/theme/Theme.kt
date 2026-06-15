package com.arthurabreu.allthingsandroid.ui.theme

import androidx.compose.runtime.Composable
import com.arthurabreu.allthingsandroid.core.designsystem.theme.AllThingsAndroidTheme as DesignSystemTheme

// Thin shim: delegates to :core:designsystem so :app screens stay unchanged.
@Composable
fun AllThingsAndroidTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) = DesignSystemTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)