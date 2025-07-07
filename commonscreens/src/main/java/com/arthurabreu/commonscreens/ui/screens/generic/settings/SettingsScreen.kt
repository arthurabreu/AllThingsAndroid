package com.arthurabreu.commonscreens.ui.screens.generic.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun SettingsScreen(
    onBack:() -> Unit
) {
    Column {
        Text(
            modifier = Modifier.testTag("SettingsScreen"),
            text = "Settings"
        )
        Button(
            modifier = Modifier.testTag("SettingsScreen_BackButton"),
            onClick = { onBack() }
        ) {
            Text("Back")
        }
    }
}