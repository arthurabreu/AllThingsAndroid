package com.arthurabreu.allthingsandroid.feature.maps

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class MapsScreenTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsScreen() {
        rule.setContent { MapsScreen(MapsViewModel()) }
        rule.onNodeWithTag("maps-screen").assertIsDisplayed()
    }
}
