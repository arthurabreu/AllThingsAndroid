package com.arthurabreu.allthingsandroid.feature.voice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class VoiceScreenTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsScreen() {
        rule.setContent { VoiceScreen(VoiceViewModel()) }
        rule.onNodeWithTag("voice-screen").assertIsDisplayed()
    }
}
