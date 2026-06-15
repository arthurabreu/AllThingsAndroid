package com.arthurabreu.allthingsandroid.core.testing

import com.arthurabreu.allthingsandroid.core.testing.InstantTaskExecutorExtension
import com.arthurabreu.allthingsandroid.core.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(
    MainDispatcherRule::class, // For coroutines
    InstantTaskExecutorExtension::class // For LiveData/Arch Components
)
abstract class BaseViewModelTest