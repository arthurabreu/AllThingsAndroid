package com.arthurabreu.allthingsandroid.feature.firebase

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class FirebaseScreenTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsScreen() {
        rule.setContent { FirebaseScreen(FirebaseViewModel()) }
        rule.onNodeWithTag("firebase-screen").assertIsDisplayed()
    }
}
