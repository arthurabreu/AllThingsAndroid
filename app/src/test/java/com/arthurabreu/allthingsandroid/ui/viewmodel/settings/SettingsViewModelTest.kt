package com.arthurabreu.allthingsandroid.ui.viewmodel.settings

import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class SettingsViewModelTest {
    private lateinit var viewModelTest: SettingsViewModel
    private val mockAppNavigator: AppNavigator = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        viewModelTest = SettingsViewModel(mockAppNavigator)
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onBackButtonClicked is called, Then tryNavigateBack is invoked on AppNavigator")
    fun onBackButtonClicked() {
        // When
        viewModelTest.onBackButtonClicked()

        // Then
        verify { mockAppNavigator.tryNavigateBack() }
    }
}