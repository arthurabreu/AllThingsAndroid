package com.arthurabreu.allthingsandroid.feature.lab

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue

class LeaksViewModelTest {
    @Test
    fun armsAndDisarms() {
        val vm = LeaksViewModel()
        vm.toggleLeak()
        assertTrue(vm.state.value.armed)
        assertNotNull(LeakHolder.ref)
        vm.toggleLeak()
        assertFalse(vm.state.value.armed)
        assertNull(LeakHolder.ref)
    }
}

