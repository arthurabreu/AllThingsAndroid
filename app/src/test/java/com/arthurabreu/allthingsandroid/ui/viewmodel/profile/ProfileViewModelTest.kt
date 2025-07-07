package com.arthurabreu.allthingsandroid.ui.viewmodel.profile

import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.ui.viewmodel.BaseViewModelTest
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class ProfileViewModelTest : BaseViewModelTest() {
    private lateinit var viewModel: ProfileViewmodel
    private val mockAppNavigator: AppNavigator = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        viewModel = ProfileViewmodel(mockAppNavigator)
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onBack is called, Then tryNavigateBack is invoked on AppNavigator")
    fun givenViewModelIsInitialized_whenOnBack_thenTryNavigateBackIsInvoked() {
        // When
        viewModel.onBack()

        // Then
        verify { mockAppNavigator.tryNavigateBack() }
    }
}