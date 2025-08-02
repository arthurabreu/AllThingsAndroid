package com.arthurabreu.allthingsandroid.ui.screen.meditation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import com.arthurabreu.allthingsandroid.R
import com.arthurabreu.allthingsandroid.ui.screen.BaseComposeKoinTest
import com.arthurabreu.allthingsandroid.ui.theme.AllThingsAndroidTheme
import org.junit.Test
import org.koin.core.module.Module
import org.koin.dsl.module

class HomeMeditationUiTest : BaseComposeKoinTest() {
    override fun provideTestModules(): List<Module> {
        return listOf(module {
            // define any dependencies needed for the test
        })
    }

    override fun setComposeContent() {
        composeTestRule.setContent {
            AllThingsAndroidTheme {
                HomeMeditation()
            }
        }
    }

    @Test
    fun homeMeditation_greetingSection_isDisplayed() {
        composeTestRule.onNodeWithText(
            context.getString(
                R.string.good_morning,
                context.getString(R.string.arthur_abreu)
            )
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            context.getString(
                R.string.we_wish_you_have_a_good_day
            )
        ).assertIsDisplayed()
    }

    @Test
    fun homeMeditation_chipSection_isDisplayed() {
        composeTestRule.onNodeWithText(context.getString(R.string.sweet_sleep)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.insomnia)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.depression)).assertIsDisplayed()
    }

    @Test
    fun homeMeditation_currentMeditationSection_isDisplayed() {
        composeTestRule.onNodeWithText(context.getString(R.string.daily_thought)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.meditation_3_10_min)).assertIsDisplayed()
    }

    @Test
    fun homeMeditation_featureMeditationSection_isDisplayed() {
        composeTestRule.onNodeWithText(context.getString(R.string.featuremeditations)).assertIsDisplayed()
        composeTestRule.onNodeWithText("Sleep meditation").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tips for sleeping").assertIsDisplayed()
        composeTestRule.onNodeWithText("Night island").assertIsDisplayed()
        composeTestRule.onNodeWithText("Calming sounds").assertIsDisplayed()
    }

    @Test
    fun homeMeditation_bottomMenu_isDisplayed() {
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
        composeTestRule.onNodeWithText("Meditate").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sleep").assertIsDisplayed()
        composeTestRule.onNodeWithText("Music").assertIsDisplayed()
        composeTestRule.onNodeWithText("Profile").assertIsDisplayed()
    }
}