package com.arthurabreu.allthingsandroid.feature.firebase

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class FirebaseViewModelTest {
    @Test
    fun authAndNoteAndUpload() {
        val vm = FirebaseViewModel()
        vm.toggleAuth()
        vm.addNote()
        vm.upload()
        vm.crash()
        val snap = vm.snapshot()
        assertTrue(snap.signedIn)
        assertEquals("demo@local", snap.userLabel)
        assertEquals(1, snap.notes.size)
        assertTrue(vm.state.value.crashed)
    }
}

