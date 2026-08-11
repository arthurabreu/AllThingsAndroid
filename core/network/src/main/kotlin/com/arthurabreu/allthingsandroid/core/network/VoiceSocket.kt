package com.arthurabreu.allthingsandroid.core.network

import com.arthurabreu.allthingsandroid.core.common.AppResult

interface VoiceTransport {
    fun sendPcm(bytes: ByteArray): AppResult<ByteArray>
}

class LocalEchoTransport : VoiceTransport {
    override fun sendPcm(bytes: ByteArray): AppResult<ByteArray> =
        AppResult.Ok(bytes.copyOf())
}
