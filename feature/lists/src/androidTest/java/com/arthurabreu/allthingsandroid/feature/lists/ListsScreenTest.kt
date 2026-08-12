package com.arthurabreu.allthingsandroid.feature.lists

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class ListsScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun showsInboxByDefaultAndCanSwitchToBoard() {
        rule.setContent { ListsScreen(ListsViewModel()) }
        rule.onNodeWithTag("lists-screen").assertIsDisplayed()
        rule.onNodeWithTag("lists-inbox").assertIsDisplayed()
        rule.onNodeWithTag("lists-layout-board").performClick()
        rule.onNodeWithTag("lists-board").assertIsDisplayed()
    }

    @Test
    fun paginatesAndShowsEmptyOnMiss() {
        val vm = ListsViewModel()
        rule.setContent { ListsScreen(vm) }
        rule.onNodeWithTag("lists-page-prev").assertIsNotEnabled()
        rule.onNodeWithTag("lists-page-next").assertIsEnabled().performClick()
        rule.onNodeWithTag("lists-page-prev").assertIsEnabled()
        rule.onNodeWithTag("lists-search").performTextInput("zzz-no-match")
        rule.onNodeWithTag("lists-empty").assertIsDisplayed()
    }

    @Test
    fun errorThenRetryRestoresList() {
        rule.setContent { ListsScreen(ListsViewModel()) }
        rule.onNodeWithTag("lists-fail").performClick()
        rule.onNodeWithTag("lists-error").assertIsDisplayed()
        rule.onNodeWithTag("lists-retry").performClick()
        rule.onNodeWithTag("lists-inbox").assertIsDisplayed()
    }
}
