package com.arthurabreu.allthingsandroid.ui.screen.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arthurabreu.allthingsandroid.ui.screen.BaseComposeKoinTest
import com.arthurabreu.allthingsandroid.ui.theme.AllThingsAndroidTheme
import com.arthurabreu.allthingsandroid.ui.viewmodel.settings.SettingsViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class SettingsScreenTest : BaseComposeKoinTest() {
    private val mockViewModel: SettingsViewModel = mockk(relaxed = true)

    override fun provideTestModules() = listOf(
        module { factory { mockViewModel } }
    )

    override fun setComposeContent() {
        composeTestRule.setContent {
            AllThingsAndroidTheme {
                SettingsScreen(
                    viewModel = mockViewModel
                )
            }
        }
    }

    @Test
    fun givenSettingsScreenIsDisplayed_thenItIsVisible() {
        composeTestRule.onNodeWithTag(SETTINGS_SCREEN_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun whenBackButtonClicked_thenViewModelOnBackButtonClickedIsCalled() {
        composeTestRule.onNodeWithTag(BACK_BUTTON_TAG)
            .assertIsDisplayed()
            .performClick()

        verify { mockViewModel.onBackButtonClicked() }
    }

    companion object TestTags {
        const val SETTINGS_SCREEN_TAG = "SettingsScreen"
        const val BACK_BUTTON_TAG = "SettingsScreen_BackButton"
    }
}