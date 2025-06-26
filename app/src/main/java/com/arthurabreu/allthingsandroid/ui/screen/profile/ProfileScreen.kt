package com.arthurabreu.allthingsandroid.ui.screen.profile

import androidx.compose.runtime.Composable
import com.arthurabreu.allthingsandroid.ui.viewmodel.profile.ProfileViewmodel
import org.koin.androidx.compose.koinViewModel
import com.arthurabreu.commonscreens.ui.screens.generic.profile.ProfileScreen

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