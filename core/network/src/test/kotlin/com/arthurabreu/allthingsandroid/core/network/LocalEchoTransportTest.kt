package com.arthurabreu.allthingsandroid.core.network

import com.arthurabreu.allthingsandroid.core.common.AppResult
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

class LocalEchoTransportTest {
    @Test
    fun echoesBytes() {
        val result = LocalEchoTransport().sendPcm(byteArrayOf(1, 2, 3)) as AppResult.Ok
        assertArrayEquals(byteArrayOf(1, 2, 3), result.value)
    }
}
