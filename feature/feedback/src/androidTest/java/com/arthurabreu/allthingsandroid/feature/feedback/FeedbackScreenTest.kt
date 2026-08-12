package com.arthurabreu.allthingsandroid.feature.feedback

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class FeedbackScreenTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsScreen() {
        rule.setContent { FeedbackScreen(FeedbackViewModel()) }
        rule.onNodeWithTag("feedback-screen").assertIsDisplayed()
    }
}
