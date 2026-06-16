package com.arthurabreu.commonscreens.ui.screens.generic.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppScaffold

@Composable
fun SettingsScreen(
    onBack:() -> Unit
) {
    AppScaffold(title = "Settings", onBack = onBack) { padding ->
        Column(modifier = Modifier.padding(padding)) {
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
}