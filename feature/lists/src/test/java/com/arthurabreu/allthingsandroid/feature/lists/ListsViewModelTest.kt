package com.arthurabreu.allthingsandroid.feature.lists

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ListsViewModelTest {
    @Test
    fun filtersBlockedAndResetsPage() {
        val vm = ListsViewModel()
        vm.nextPage()
        assertEquals(1, vm.state.value.page)
        vm.onQuery("blocked")
        assertEquals(0, vm.state.value.page)
        assertTrue(vm.state.value.visible.all { it.body.contains("Blocked") })
        assertTrue(vm.state.value.totalCount < 80)
    }

    @Test
    fun pagesForwardAndBack() {
        val vm = ListsViewModel()
        assertEquals(20, vm.state.value.visible.size)
        assertEquals("1–20 of 80", vm.state.value.rangeLabel)
        assertTrue(vm.state.value.hasNext)
        assertFalse(vm.state.value.hasPrevious)

        vm.nextPage()
        assertEquals(1, vm.state.value.page)
        assertEquals("21–40 of 80", vm.state.value.rangeLabel)
        assertTrue(vm.state.value.hasPrevious)

        vm.previousPage()
        assertEquals(0, vm.state.value.page)
    }

    @Test
    fun switchesLayout() {
        val vm = ListsViewModel()
        assertEquals(ListsLayout.Inbox, vm.state.value.layout)
        vm.setLayout(ListsLayout.Board)
        assertEquals(ListsLayout.Board, vm.state.value.layout)
    }

    @Test
    fun failThenRetryClearsError() {
        val vm = ListsViewModel()
        vm.fail()
        assertEquals("Network unavailable", vm.state.value.error)
        assertTrue(vm.state.value.visible.isEmpty())
        vm.retry()
        assertNull(vm.state.value.error)
        assertTrue(vm.state.value.visible.isNotEmpty())
    }
}
