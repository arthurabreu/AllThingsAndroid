package com.arthurabreu.allthingsandroid.utils

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
class CpfUtilsTest {
    @Test
    @DisplayName("Given a string of digits, When formatPartialCpf is called, Then it formats the CPF correctly")
    fun givenStringOfDigits_whenFormatPartialCpfCalled_thenFormatsCorrectly() {
        val partial = "12345678901"
        val formatted = CpfUtils.formatPartialCpf(partial)
        assertEquals("123.456.789-01", formatted)
    }

    @Test
    @DisplayName("Given a valid CPF, When isCpfValid is called, Then it returns true")
    fun givenValidCpf_whenIsCpfValidCalled_thenReturnsTrue() {
        val validCpf = "123.456.789-01"
        assertTrue(CpfUtils.isCpfValid(validCpf))
    }

    @Test
    @DisplayName("Given an invalid CPF, When isCpfValid is called, Then it returns false")
    fun givenInvalidCpf_whenIsCpfValidCalled_thenReturnsFalse() {
        val invalidCpf = "123.4567.890-1"
        assertFalse(CpfUtils.isCpfValid(invalidCpf))
    }
}