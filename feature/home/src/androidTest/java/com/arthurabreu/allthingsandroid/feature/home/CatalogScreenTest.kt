package com.arthurabreu.allthingsandroid.feature.home

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.assertIsDisplayed
import org.junit.Rule
import org.junit.Test

class CatalogScreenTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsCatalogList() {
        rule.setContent { CatalogScreen(CatalogViewModel()) { } }
        rule.onNodeWithTag("catalog-list").assertIsDisplayed()
    }
}
