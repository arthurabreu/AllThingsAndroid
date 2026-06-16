package com.arthurabreu.allthingsandroid.feature.player.di

import com.arthurabreu.allthingsandroid.feature.player.presentation.viewmodel.PlayerViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val playerModule = module {
    viewModelOf(::PlayerViewModel)
}
