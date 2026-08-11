package com.arthurabreu.allthingsandroid.feature.voice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import com.arthurabreu.allthingsandroid.core.common.AppResult
import com.arthurabreu.allthingsandroid.core.network.LocalEchoTransport
import com.arthurabreu.allthingsandroid.core.network.VoiceTransport

data class VoiceState(
    val status: String = "Idle — server optional, local echo fallback",
    val recorded: Int = 0,
    val received: Int = 0,
    val buffer: ByteArray = ByteArray(0),
)

class VoiceViewModel(
    private val transport: VoiceTransport = LocalEchoTransport(),
) : ViewModel() {
    private val _state = MutableStateFlow(VoiceState())
    val state: StateFlow<VoiceState> = _state.asStateFlow()

    fun record() {
        val chunk = ByteArray(320) { 1 }
        _state.update { it.copy(buffer = it.buffer + chunk, recorded = it.recorded + chunk.size, status = "Recorded") }
    }

    fun send() {
        when (val result = transport.sendPcm(_state.value.buffer)) {
            is AppResult.Ok -> _state.update {
                it.copy(received = result.value.size, status = "Played local echo", buffer = ByteArray(0))
            }
            is AppResult.Err -> _state.update { it.copy(status = result.message) }
        }
    }
}


@Composable
fun VoiceScreen(viewModel: VoiceViewModel, onBack: () -> Unit = {}) {
    val state by viewModel.state.collectAsState()
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).testTag("voice-screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Voice", style = MaterialTheme.typography.headlineSmall)

        Text(state.status, modifier = Modifier.testTag("voice-status"))
        Button(onClick = viewModel::record) { Text("Record chunk") }
        Button(onClick = viewModel::send) { Text("Send to server") }
        Text("Bytes in: ${state.recorded}  out: ${state.received}")

        Button(onClick = onBack) { Text("Back") }
    }
}
