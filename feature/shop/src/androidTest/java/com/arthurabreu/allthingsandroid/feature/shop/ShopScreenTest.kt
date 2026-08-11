package com.arthurabreu.allthingsandroid.feature.shop

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class ShopScreenTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsScreen() {
        rule.setContent { ShopScreen(ShopViewModel()) }
        rule.onNodeWithTag("shop-screen").assertIsDisplayed()
    }
}
