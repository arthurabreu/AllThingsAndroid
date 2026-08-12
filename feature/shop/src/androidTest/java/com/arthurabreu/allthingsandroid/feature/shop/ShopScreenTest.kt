package com.arthurabreu.allthingsandroid.feature.shop

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ShopScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun showsScreen() {
        rule.setContent { ShopScreen(ShopViewModel()) }
        rule.onNodeWithTag("shop-screen").assertIsDisplayed()
        rule.onNodeWithTag("add-sku-lager").assertIsDisplayed()
    }

    @Test
    fun opensCartSheet() {
        rule.setContent { ShopScreen(ShopViewModel()) }
        rule.onNodeWithTag("cart-open").performClick()
        rule.onNodeWithTag("cart-sheet-content").assertIsDisplayed()
        rule.onNodeWithTag("cart-empty").assertIsDisplayed()
    }

    @Test
    fun addThenOpenCartShowsLineAndTotal() {
        val vm = ShopViewModel()
        rule.setContent { ShopScreen(vm) }
        rule.onNodeWithTag("add-sku-lager").performClick()
        rule.onNodeWithTag("cart-open").performClick()
        rule.onNodeWithTag("cart-line-sku-lager").assertIsDisplayed()
        rule.onNodeWithTag("cart-total").assertIsDisplayed()
    }
}
