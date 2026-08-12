package com.arthurabreu.allthingsandroid.feature.chat

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class ChatViewModelTest {
    @Test
    fun sendAcksWhenOnline() {
        val vm = ChatViewModel()
        vm.onDraft("hi")
        vm.send()
        assertEquals(3, vm.state.value.messages.size)
        assertTrue(vm.state.value.messages.last().body.startsWith("ack:"))
    }

    @Test
    fun offlineQueuesWithoutAck() {
        val vm = ChatViewModel()
        vm.toggleOffline()
        vm.onDraft("later")
        vm.send()
        assertTrue(vm.state.value.messages.last().pending)
    }
}

