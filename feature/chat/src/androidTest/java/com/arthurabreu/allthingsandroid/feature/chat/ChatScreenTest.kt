package com.arthurabreu.allthingsandroid.feature.chat

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class ChatScreenTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsScreen() {
        rule.setContent { ChatScreen(ChatViewModel()) }
        rule.onNodeWithTag("chat-screen").assertIsDisplayed()
    }
}
