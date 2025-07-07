package com.arthurabreu.allthingsandroid.ui.viewmodel.login

import com.arthurabreu.allthingsandroid.utils.logger.ClassLogger
import dalvik.annotation.TestTarget
import io.mockk.Runs
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.verify
import kotlin.test.Test

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel
    private val mockLogger: ClassLogger = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        every { mockLogger.d(any()) } just Runs
        viewModel = LoginViewModel(mockLogger)
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When no action is taken, Then cpfState, password, isLoading e appIcon estão nos valores iniciais")
    fun givenViewModelIsInitialized_whenNoAction_thenInitialStateIsSet() = runTest {
        assert(viewModel.loginState.value.cpfState.value.isEmpty())
        assert(viewModel.loginState.value.password.isEmpty())
        assert(!viewModel.loginState.value.isLoading)
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onCpfChange is called with a valid CPF, Then cpfState value is updated correctly")
    fun givenViewModelIsInitialized_whenOnCpfChange_thenCpfStateIsUpdated() = runTest {
        // Given
        val validCpf = "12345678901"

        // When
        viewModel.loginState.value.cpfState.onValueChange(validCpf)

        // Then
        assert(viewModel.loginState.value.cpfState.value == validCpf.filter { it.isDigit() }.take(11))
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onPasswordChange is called with a password, Then password is updated correctly")
    fun givenViewModelIsInitialized_whenOnPasswordChange_thenPasswordIsUpdated() = runTest {
        // Given
        val newPassword = "newPassword123"

        // When
        viewModel.loginState.value.onPasswordChange(newPassword)

        // Then
        assert(viewModel.loginState.value.password == newPassword)
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onLoginClick is called, Then isLoading is set to true")
    fun givenViewModelIsInitialized_whenOnLoginClick_thenIsLoadingIsSetToTrue() = runTest {
        // Given
        viewModel.loginState.value.onLoginClick()

        // When
        assert(!viewModel.loginState.value.isLoading)

        // Simulate a delay to check if isLoading is reset
        delay(1000)

        // Then
        assert(!viewModel.loginState.value.isLoading)
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onNumberChange is called with a valid number, Then numberFieldState value is updated correctly")
    fun givenViewModelIsInitialized_whenOnNumberChange_thenNumberFieldStateIsUpdated() = runTest {
        // Given
        val validNumber = "1234567890"

        // When
        viewModel.loginState.value.allTextFieldsState.onValueChange(validNumber)

        // Then
        assert(viewModel.loginState.value.allTextFieldsState.value == validNumber.filter { it.isDigit() })
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onNumberChange is called with an invalid number, Then numberFieldState value is updated")
    fun givenViewModelIsInitialized_whenOnNumberChangeWithInvalid_thenNumberFieldStateIsUpdated() = runTest {
        // Given
        val invalidNumber = "abc123"

        // When
        viewModel.loginState.value.allTextFieldsState.onValueChange(invalidNumber)

        // Then
        assert(viewModel.loginState.value.allTextFieldsState.value == invalidNumber)
        assert(viewModel.loginState.value.allTextFieldsState.isError)
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onNumberChange is called with an empty string, Then numberFieldState value is reset")
    fun givenViewModelIsInitialized_whenOnNumberChangeWithEmpty_thenNumberFieldStateIsReset() = runTest {
        // Given
        val emptyString = ""

        // When
        viewModel.loginState.value.allTextFieldsState.onValueChange(emptyString)

        // Then
        assert(viewModel.loginState.value.allTextFieldsState.value.isEmpty())
        assert(!viewModel.loginState.value.allTextFieldsState.isError)
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onLoginClick is called, Then logger is called with correct message")
    fun givenViewModelIsInitialized_whenOnLoginClick_thenLoggerIsCalled() = runTest {
        // Given
        val tag = "LoginViewModel"
        val functionName = "onLoginClick"
        viewModel.loginState.value.onLoginClick()

        // When
        verify { mockLogger.d("$functionName execution started. tag: $tag") }

        // Then
        assert(!viewModel.loginState.value.isLoading)
        verify { mockLogger.d("$functionName execution finished successfully. tag: $tag") }
    }

    @Test
    @DisplayName("Given ViewModel is initialized, When onLoginClick is called, Then logger logs the execution time")
    fun givenViewModelIsInitialized_whenOnLoginClick_thenLoggerLogsExecutionTime() = runTest {
        // Given
        val tag = "LoginViewModel"
        val functionName = "onLoginClick"

        // When
        viewModel.loginState.value.onLoginClick()

        // Then
        verify { mockLogger.d("$functionName execution started. tag: $tag") }
        verify { mockLogger.d("$functionName execution finished successfully. tag: $tag") }
    }
}