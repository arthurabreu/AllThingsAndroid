package com.arthurabreu.allthingsandroid.core.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CartLineTest {
    @Test
    fun lineTotalMultipliesPrice() {
        val product = ShopProduct("1", "Beer", 500, 10)
        assertEquals(1500, CartLine(product, 3).lineTotalCents)
    }
}
