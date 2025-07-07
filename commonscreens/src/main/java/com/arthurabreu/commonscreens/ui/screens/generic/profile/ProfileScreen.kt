package com.arthurabreu.commonscreens.ui.screens.generic.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun ProfileScreen(
    userId: String,
    onBack: () -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.testTag("CommonProfileScreen_UserIdText"),
            text = "Profile Screen - User: $userId"
        )
        Button(
            modifier = Modifier.testTag("CommonProfileScreen_BackButton"),
            onClick = { onBack() }
        ) {
            Text("Back")
        }
    }
}