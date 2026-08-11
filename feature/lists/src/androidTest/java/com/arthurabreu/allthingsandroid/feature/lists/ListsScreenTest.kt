package com.arthurabreu.allthingsandroid.feature.lists

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class ListsScreenTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsScreen() {
        rule.setContent { ListsScreen(ListsViewModel()) }
        rule.onNodeWithTag("lists-screen").assertIsDisplayed()
    }
}
