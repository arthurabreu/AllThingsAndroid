package com.arthurabreu.allthingsandroid.ui.viewmodel.download

import androidx.compose.ui.graphics.Color
import com.arthurabreu.allthingsandroid.ui.states.DownloadState
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class DownloadViewmodelTest {

    private lateinit var viewModel: DownloadViewModel

    @Test
    @DisplayName("Given ViewModel is initialized, When no action is taken, Then downloadState is Idle")
    fun givenViewModelIsInitialized_whenNoAction_thenDownloadStateIsIdle() = runTest {
        // Given
        viewModel = DownloadViewModel()
        // Then
        assertEquals(DownloadState.Idle, viewModel.downloadState.value)
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When no action is taken, Then progressColor is Blue")
    fun givenViewModelIsInitialized_whenNoAction_thenProgressColorIsBlue() = runTest {
        // Given
        viewModel = DownloadViewModel()
        // Then
        assertEquals(Color.Blue, viewModel.progressColor.value)
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When no action is taken, Then showColorPicker is false")
    fun givenViewModelIsInitialized_whenNoAction_thenShowColorPickerIsFalse() = runTest {
        // Given
        viewModel = DownloadViewModel()
        // Then
        assertFalse(viewModel.showColorPicker.value)
    }


    @Test
    @DisplayName("Given showColorPicker is false, When showColorPicker is called, Then showColorPicker becomes true")
    fun givenShowColorPickerIsFalse_whenShowColorPickerIsCalled_thenShowColorPickerIsTrue() = runTest {
        // When
        viewModel = DownloadViewModel()

        assertFalse(viewModel.showColorPicker.value, "Initial showColorPicker should be false.")

        viewModel.showColorPicker()
        runCurrent() // Ensure the StateFlow update is processed

        // Then
        assertTrue(viewModel.showColorPicker.value, "showColorPicker should be true after calling showColorPicker().")
    }

    @Test
    @DisplayName("Given showColorPicker is true, When hideColorPicker is called, Then showColorPicker becomes false")
    fun givenShowColorPickerIsTrue_whenHideColorPickerIsCalled_thenShowColorPickerIsFalse() = runTest {
        // When
        viewModel = DownloadViewModel()

        viewModel.showColorPicker()
        runCurrent()
        assertTrue(viewModel.showColorPicker.value, "Precondition: showColorPicker should be true.")

        viewModel.hideColorPicker()
        runCurrent() // Ensure the StateFlow update is processed

        // Then
        assertFalse(viewModel.showColorPicker.value, "showColorPicker should be false after calling hideColorPicker().")
    }

    @Test
    @DisplayName("Given a new color is selected, When saveColor is called, Then progressColor updates")
    fun givenNewColor_whenSaveColor_thenProgressColorUpdates() = runTest {
        // Given
        viewModel = DownloadViewModel()
        val newColor = Color.Blue

        // When
        viewModel.saveColor(newColor)
        runCurrent() // Execute the coroutine launched by saveColor

        // Then
        assertEquals(newColor, viewModel.progressColor.value)
    }
}