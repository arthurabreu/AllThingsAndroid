package com.arthurabreu.allthingsandroid.ui.features.basics

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    onProfileClick: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    Column {
        Button(onClick = { onProfileClick("user123") }) {
            Text("Go to Profile")
        }
        Button(onClick = onSettingsClick) {
            Text("Go to Settings")
        }
    }
}