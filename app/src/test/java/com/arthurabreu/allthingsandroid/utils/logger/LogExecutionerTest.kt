package com.arthurabreu.allthingsandroid.utils.logger

import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@Suppress("JUnitMalformedDeclaration")
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Required for @BeforeAll
@ExtendWith(MockKExtension::class)
class LogExecutionerTest {
    private val mockLogger = mockk<Logger>(relaxed = true)

    @Test
    @DisplayName("Given a successful suspend block, When logApiExecution is invoked, Then logs start and completed messages")
    fun givenSuccessfulBlock_whenLogApiExecutionInvoked_thenLogsStartAndCompleted() = runTest {
        logApiExecution(mockLogger) { "Result" }
        verifyOrder {
            mockLogger.d(match { it.contains("Execution Started:") })
            mockLogger.d(match { it.contains("Execution Completed:") })
        }
    }

    @Test
    @DisplayName("Given a failing suspend block, When logApiExecution is invoked, Then logs start and failed messages, rethrowing exception")
    fun givenFailingBlock_whenLogApiExecutionInvoked_thenLogsAndRethrows() = runTest {
        assertThrows<RuntimeException> {
            logApiExecution(mockLogger) { throw RuntimeException("Test error") }
        }
        verify {
            mockLogger.d(match { it.contains("Execution Started:") })
            mockLogger.e(match { it.contains("Execution Failed:") }, any())
        }
    }

    @Test
    @DisplayName("Given a successful suspend block, When logExecution is invoked, Then logs start and finishing messages")
    fun givenSuccessfulBlock_whenLogExecutionInvoked_thenLogsStartAndFinish() = runTest {
        val result = logExecution(mockLogger, "testTag", "testFunc") { 123 }
        assertEquals(123, result)
        verifyOrder {
            mockLogger.d("testFunc execution started. tag: testTag")
            mockLogger.d("testFunc execution finished successfully. tag: testTag")
        }
    }

    @Test
    @DisplayName("Given a failing suspend block, When logExecution is invoked, Then logs start and returns null")
    fun givenFailingBlock_whenLogExecutionInvoked_thenLogsStartAndReturnsNull() = runTest {
        val result = logExecution(mockLogger, "testTag", "testFunc") { throw Exception("Error") }
        assertNull(result)
        verify {
            mockLogger.d("testFunc execution started. tag: testTag")
            mockLogger.e(match { it.contains("failed with an exception") }, any())
        }
    }
}