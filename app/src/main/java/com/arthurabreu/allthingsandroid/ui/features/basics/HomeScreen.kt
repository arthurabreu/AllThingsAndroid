package com.arthurabreu.allthingsandroid.ui.features.basics

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    Column {
        Button(
            onClick = { viewModel.onProfileClick() }
        ) {
            Text("Go to Profile")
        }
        Button(
            onClick = { viewModel.onSettingsClick() }
        ) {
            Text("Go to Settings")
        }
    }
}