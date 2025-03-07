package com.arthurabreu.allthingsandroid.ui.features.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    userId: String,
    viewModel: ProfileViewmodel = koinViewModel()
) {
    Column {
        Text("Profile Screen - User: $userId")
        Button(
            onClick = { viewModel.onBack() }
        ) {
            Text("Back")
        }
    }
}