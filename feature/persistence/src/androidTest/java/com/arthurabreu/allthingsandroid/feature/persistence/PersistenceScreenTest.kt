package com.arthurabreu.allthingsandroid.feature.persistence

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class PersistenceScreenTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsScreen() {
        rule.setContent { PersistenceScreen(PersistenceViewModel()) }
        rule.onNodeWithTag("persistence-screen").assertIsDisplayed()
    }
}
