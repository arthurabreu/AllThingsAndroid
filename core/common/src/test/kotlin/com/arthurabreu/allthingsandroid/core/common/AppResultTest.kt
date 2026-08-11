package com.arthurabreu.allthingsandroid.core.common

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AppResultTest {
    @Test
    fun okHoldsValue() {
        val result = AppResult.Ok(2)
        assertTrue(result.isOk)
        assertEquals(2, result.getOrNull())
    }

    @Test
    fun runAppCatchingMapsException() {
        val result = runAppCatching<Int> { error("boom") }
        assertTrue(result is AppResult.Err)
        assertEquals("boom", (result as AppResult.Err).message)
    }
}
