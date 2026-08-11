package com.arthurabreu.allthingsandroid.feature.lists

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue

class ListsViewModelTest {
    @Test
    fun filtersBlocked() {
        val vm = ListsViewModel()
        vm.onQuery("blocked")
        assertTrue(vm.state.value.visible.all { it.body.contains("Blocked") })
    }

    @Test
    fun failThenRetryClearsError() {
        val vm = ListsViewModel()
        vm.fail()
        assertEquals("Network unavailable", vm.state.value.error)
        vm.retry()
        assertNull(vm.state.value.error)
        assertTrue(vm.state.value.visible.isNotEmpty())
    }
}

