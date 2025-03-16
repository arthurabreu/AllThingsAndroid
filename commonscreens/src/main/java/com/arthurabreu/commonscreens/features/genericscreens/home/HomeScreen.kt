package com.arthurabreu.commonscreens.features.genericscreens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onDownloadClick: () -> Unit,
    json: String,
) {
    Column {
        Button(
            onClick = { onProfileClick() }
        ) {
            Text("Go to Profile")
        }
        Button(
            onClick = { onSettingsClick() }
        ) {
            Text("Go to Settings")
        }
        Button(
            onClick = { onDownloadClick() }
        ) {
            Text("Go to Downloads")
        }
        Text(text = "\nDomain data from api -> db: \n\n${json}")
    }
}