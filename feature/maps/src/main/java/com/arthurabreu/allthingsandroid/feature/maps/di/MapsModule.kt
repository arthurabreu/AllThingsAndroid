package com.arthurabreu.allthingsandroid.feature.maps.di

import com.arthurabreu.allthingsandroid.feature.maps.presentation.viewmodel.MapsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mapsModule = module {
    viewModel { MapsViewModel() }
}
