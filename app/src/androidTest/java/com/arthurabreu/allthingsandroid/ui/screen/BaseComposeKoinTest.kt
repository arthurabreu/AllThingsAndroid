package com.arthurabreu.allthingsandroid.ui.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.koin.core.module.Module

abstract class BaseComposeKoinTest {

    val context = InstrumentationRegistry.getInstrumentation().targetContext!!

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val koinTestRule = KoinTestRule(provideTestModules())

    /**
     * Provide the necessary Koin modules for the test.
     */
    abstract fun provideTestModules(): List<Module>

    /**
     * Provide the Compose content to be tested.
     */
    abstract fun setComposeContent()

    @Before
    fun baseSetUp() {
        setComposeContent()
    }
}