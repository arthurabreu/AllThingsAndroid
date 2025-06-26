package com.arthurabreu.commonscreens.ui.screens.generic.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onButtonsClick: () -> Unit,
    onListsClick: () -> Unit,
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
        Button(
            onClick = { onButtonsClick() }
        ) {
            Text("Go to Buttons Example")
        }
        Button(
            onClick = { onListsClick() }
        ) {
            Text("Go to Lists Example")
        }
        Text(text = "\nDomain data from api -> db: \n\n${json}")
    }
}