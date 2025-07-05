package com.arthurabreu.allthingsandroid.ui.viewmodel

import com.arthurabreu.allthingsandroid.util.InstantTaskExecutorExtension
import com.arthurabreu.allthingsandroid.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(
    MainDispatcherRule::class, // For coroutines
    InstantTaskExecutorExtension::class // For LiveData/Arch Components
)
abstract class BaseViewModelTest