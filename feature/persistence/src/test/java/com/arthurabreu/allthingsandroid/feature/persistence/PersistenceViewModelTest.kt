package com.arthurabreu.allthingsandroid.feature.persistence

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class PersistenceViewModelTest {
    @Test
    fun startsAtV3() {
        assertEquals(3, PersistenceViewModel().state.value.currentVersion)
    }

    @Test
    fun seedAppendsNote() {
        val vm = PersistenceViewModel()
        vm.seedNote()
        assertTrue(vm.state.value.notes.first().contains("v3"))
    }
}

