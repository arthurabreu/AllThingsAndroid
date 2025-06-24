package com.arthurabreu.allthingsandroid.ui.features.profile

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel
import com.arthurabreu.commonscreens.features.generic.profile.ProfileScreen

@Composable
fun ProfileScreen(
    userId: String,
    viewModel: ProfileViewmodel = koinViewModel()
) {

   ProfileScreen(
       userId = userId,
       onBack = { viewModel.onBack() }
    )
}