package com.arthurabreu.commonscreens.ui.screens.generic.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ProfileScreen(
    userId: String,
    onBack: () -> Unit,
) {
    Column {
        Text("Profile Screen - User: $userId")
        Button(
            onClick = { onBack() }
        ) {
            Text("Back")
        }
    }
}