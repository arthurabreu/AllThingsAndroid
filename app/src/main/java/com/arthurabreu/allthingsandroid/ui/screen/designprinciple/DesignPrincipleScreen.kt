package com.arthurabreu.allthingsandroid.ui.screen.designprinciple

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel
import com.arthurabreu.allthingsandroid.ui.viewmodel.designprinciple.DesignPrincipleViewModel

@Composable
fun DesignPrincipleScreen(
    viewModel: DesignPrincipleViewModel = koinViewModel()
) {
    DesignPrincipleComponent(
        principles = viewModel.principles
    )
}