package com.arthurabreu.allthingsandroid.utils.logger

import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Required for @BeforeAll
@ExtendWith(MockKExtension::class)
class CallerResolverTest {
    @Test
    @DisplayName("Given a valid function call, When getCallerInfo is invoked, Then it returns stack info")
    fun givenAValidFunctionCall_whenGetCallerInfoIsInvoked_thenItReturnsStackInfo() {
        val callerInfo = calledFromValidLocation()
        assertTrue(callerInfo.contains("Could not resolve caller information."))
    }

    @Test
    @DisplayName("Given no valid frame in the stack, When getCallerInfo is invoked, Then it returns an error message")
    fun givenNoValidFrame_whenGetCallerInfoIsInvoked_thenItReturnsErrorMessage() {
        val info = calledFromExcludedLocation()
        assertEquals("Could not resolve caller information.", info)
    }

    private fun calledFromValidLocation(): String {
        return CallerResolver.getCallerInfo()
    }

    private fun calledFromExcludedLocation(): String {
        return object : Any() {
            fun fakeCall(): String = CallerResolver.getCallerInfo()
        }.fakeCall()
    }
}