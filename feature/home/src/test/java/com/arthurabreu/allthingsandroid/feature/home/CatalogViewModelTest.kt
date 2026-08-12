package com.arthurabreu.allthingsandroid.feature.home

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CatalogViewModelTest {
    private val vm = CatalogViewModel()

    @Test
    fun sectionsEndWithLab() {
        assertEquals("Lab / Design System", vm.state.value.sections.last().title)
    }

    @Test
    fun routeForShop() {
        assertEquals("shop", vm.routeFor("shop"))
    }

    @Test
    fun unknownIdReturnsNull() {
        assertEquals(null, vm.routeFor("missing"))
    }

    @Test
    fun hasQualityAndField() {
        val titles = vm.state.value.sections.map { it.title }
        assertTrue(titles.containsAll(listOf("Quality", "Field", "Realtime")))
    }
}
