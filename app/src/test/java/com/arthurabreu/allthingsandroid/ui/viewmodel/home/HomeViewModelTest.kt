package com.arthurabreu.allthingsandroid.ui.viewmodel.home

import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ApiShowcaseFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ButtonsFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.DownloadFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ListsFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.LoginFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.ProfileFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.SettingsFeature
import com.arthurabreu.allthingsandroid.core.navigation.destinations.TextFieldsFeature
import com.arthurabreu.allthingsandroid.ui.viewmodel.BaseViewModelTest
import com.arthurabreu.allthingsandroid.utils.logger.ClassLogger
import io.mockk.Runs
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class HomeViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: HomeViewModel
    private val mockNavigator: AppNavigator = mockk()
    private val mockLogger: ClassLogger = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        every { mockLogger.d(any()) } just Runs
        viewModel = HomeViewModel(mockNavigator, mockLogger)
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When no action is taken, Then userdata is set to 'user123'")
    fun givenViewModelIsInitialized_whenNoAction_thenUserdataIsSet() = runTest {
        // Then
        assertEquals("user123", viewModel.userdata.value)
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onProfileClick is called with userId, Then navigator tries to navigate to Profile route")
    fun givenViewModelIsInitialized_whenOnProfileClick_thenNavigateToProfile() = runTest {
        // Given
        val testUserId = "testUser123"
        every { mockNavigator.tryNavigateTo(any()) } just Runs

        // When
        viewModel.onProfileClick(testUserId)

        // Then
        verify {
            mockNavigator.tryNavigateTo(
                match { it == ProfileFeature.Profile(userId = testUserId) }
            )
        }
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onSettingsClick is called, Then navigator tries to navigate to Settings route")
    fun givenViewModelIsInitialized_whenOnSettingsClick_thenNavigateToSettings() = runTest {
        // Given
        every { mockNavigator.tryNavigateTo(any()) } just Runs

        // When
        viewModel.onSettingsClick()

        // Then
        verify { mockNavigator.tryNavigateTo(SettingsFeature.Settings.route) }
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onDownloadClick is called, Then navigator tries to navigate to Download route")
    fun givenViewModelIsInitialized_whenOnDownloadClick_thenNavigateToDownload() = runTest {
        // Given
        every { mockNavigator.tryNavigateTo(any()) } just Runs

        // When
        viewModel.onDownloadClick()

        // Then
        verify { mockNavigator.tryNavigateTo(DownloadFeature.Download.route) }
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onButtonsClick is called, Then navigator tries to navigate to Buttons route")
    fun givenViewModelIsInitialized_whenOnButtonsClick_thenNavigateToButtons() = runTest {
        // Given
        every { mockNavigator.tryNavigateTo(any()) } just Runs

        // When
        viewModel.onButtonsClick()

        // Then
        verify { mockNavigator.tryNavigateTo(ButtonsFeature.Buttons.route) }
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onListsClick is called, Then navigator tries to navigate to Lists route")
    fun givenViewModelIsInitialized_whenOnListsClick_thenNavigateToLists() = runTest {
        // Given
        every { mockNavigator.tryNavigateTo(any()) } just Runs

        // When
        viewModel.onListsClick()

        // Then
        verify { mockNavigator.tryNavigateTo(ListsFeature.Lists.route) }
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onLoginsClick is called, Then navigator tries to navigate to Logins route")
    fun givenViewModelIsInitialized_whenOnLoginsClick_thenNavigateToLogins() = runTest {
        // Given
        every { mockNavigator.tryNavigateTo(any()) } just Runs

        // When
        viewModel.onLoginsClick()

        // Then
        verify { mockNavigator.tryNavigateTo(LoginFeature.Logins.route) }
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onLoginFakeClick is called, Then navigator tries to navigate to LoginFake route")
    fun givenViewModelIsInitialized_whenOnLoginFakeClick_thenNavigateToLoginFake() = runTest {
        // Given
        every { mockNavigator.tryNavigateTo(any()) } just Runs

        // When
        viewModel.onLoginFakeClick()

        // Then
        verify { mockNavigator.tryNavigateTo(LoginFeature.LoginFake.route) }
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onTextFieldsClick is called, Then navigator tries to navigate to TextFields route")
    fun givenViewModelIsInitialized_whenOnTextFieldsClick_thenNavigateToTextFields() = runTest {
        // Given
        every { mockNavigator.tryNavigateTo(any()) } just Runs

        // When
        viewModel.onTextFieldsClick()

        // Then
        verify { mockNavigator.tryNavigateTo(TextFieldsFeature.TextFields.route) }
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onApiShowcaseClick is called, Then navigator tries to navigate to JsonPlaceHolder route")
    fun givenViewModelIsInitialized_whenOnApiShowcaseClick_thenNavigateToJsonPlaceHolder() = runTest {
        // Given
        every { mockNavigator.tryNavigateTo(any()) } just Runs

        // When
        viewModel.onApiShowcaseClick()

        // Then
        verify { mockNavigator.tryNavigateTo(ApiShowcaseFeature.JsonPlaceHolder.route) }
    }
}