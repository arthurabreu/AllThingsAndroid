package com.arthurabreu.allthingsandroid.ui.screen.home

import androidx.compose.runtime.Composable
import com.arthurabreu.allthingsandroid.feature.home.CatalogScreen
import com.arthurabreu.allthingsandroid.feature.home.CatalogViewModel
import com.arthurabreu.allthingsandroid.ui.viewmodel.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    catalogViewModel: CatalogViewModel = koinViewModel(),
    viewModel: HomeViewModel = koinViewModel(),
) {
    CatalogScreen(viewModel = catalogViewModel, onOpen = viewModel::open)
}
