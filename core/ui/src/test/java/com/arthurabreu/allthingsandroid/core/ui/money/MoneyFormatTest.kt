package com.arthurabreu.allthingsandroid.core.ui.money

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Locale

class MoneyFormatTest {
    @Test
    fun formatsUsCurrencyFromCents() {
        assertEquals("$4.99", MoneyFormat.fromCents(499, Locale.US))
        assertEquals("$12.98", MoneyFormat.fromCents(1298, Locale.US))
        assertEquals("$0.00", MoneyFormat.fromCents(0, Locale.US))
    }
}
