package com.arthurabreu.allthingsandroid.ui.screen.profile

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.arthurabreu.allthingsandroid.ui.screen.BaseComposeKoinTest
import com.arthurabreu.allthingsandroid.ui.theme.AllThingsAndroidTheme
import com.arthurabreu.allthingsandroid.ui.viewmodel.profile.ProfileViewmodel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.koin.dsl.module

class ProfileScreenTest : BaseComposeKoinTest() {
    private val mockViewModel: ProfileViewmodel = mockk(relaxed = true)
    private val testUserId = "testUser123"

    override fun provideTestModules() = listOf(
        module { factory { mockViewModel } }
    )

    override fun setComposeContent() {
        composeTestRule.setContent {
            AllThingsAndroidTheme {
                ProfileScreen(
                    userId = testUserId,
                    viewModel = mockViewModel
                )
            }
        }
    }

    @Test
    fun givenProfileScreenIsDisplayed_whenUserIdIsProvided_thenUserIdIsShown() {
        composeTestRule.onNodeWithTag(USER_ID_TEXT_TAG)
            .assertIsDisplayed()
            .assert(hasText("Profile Screen - User: $testUserId"))
    }

    @Test
    fun givenProfileScreenIsDisplayed_whenBackButtonClicked_thenViewModelOnBackIsCalled() {
        composeTestRule.onNodeWithTag(BACK_BUTTON_TAG)
            .assertIsDisplayed()
            .performClick()

        verify { mockViewModel.onBack() }
    }

    companion object TestTags {
        const val BACK_BUTTON_TAG = "CommonProfileScreen_BackButton"
        const val USER_ID_TEXT_TAG = "CommonProfileScreen_UserIdText"
    }
}