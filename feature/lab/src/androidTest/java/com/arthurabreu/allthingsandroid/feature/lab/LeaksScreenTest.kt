package com.arthurabreu.allthingsandroid.feature.lab

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class LeaksScreenTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsScreen() {
        rule.setContent { LeaksScreen(LeaksViewModel()) }
        rule.onNodeWithTag("leaks-screen").assertIsDisplayed()
    }
}
