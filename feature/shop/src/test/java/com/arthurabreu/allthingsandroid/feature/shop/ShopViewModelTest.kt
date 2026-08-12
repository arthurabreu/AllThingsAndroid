package com.arthurabreu.allthingsandroid.feature.shop

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ShopViewModelTest {
    @Test
    fun addUpdatesTotal() {
        val vm = ShopViewModel()
        vm.add("sku-lager")
        assertEquals(499, vm.state.value.totalCents)
        assertEquals(1, vm.state.value.itemCount)
    }

    @Test
    fun stockCapShowsMessage() {
        val vm = ShopViewModel()
        repeat(4) { vm.add("sku-pack") }
        assertEquals("Out of stock", vm.state.value.message)
    }

    @Test
    fun checkoutClearsCart() {
        val vm = ShopViewModel()
        vm.add("sku-ipa")
        vm.checkout()
        assertTrue(vm.state.value.lines.isEmpty())
        assertEquals("Order placed", vm.state.value.message)
    }

    @Test
    fun removeDecrementsQuantity() {
        val vm = ShopViewModel()
        vm.add("sku-lager")
        vm.add("sku-lager")
        vm.remove("sku-lager")
        assertEquals(1, vm.state.value.itemCount)
        assertEquals(499, vm.state.value.totalCents)
    }
}
