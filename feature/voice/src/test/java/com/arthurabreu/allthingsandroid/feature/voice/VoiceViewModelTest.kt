package com.arthurabreu.allthingsandroid.feature.voice

import org.junit.jupiter.api.Test

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class VoiceViewModelTest {
    @Test
    fun recordAndEcho() = runTest {
        val vm = VoiceViewModel()
        vm.record()
        vm.send()
        assertEquals(320, vm.state.value.received)
        assertTrue(vm.state.value.status.contains("echo"))
    }
}

