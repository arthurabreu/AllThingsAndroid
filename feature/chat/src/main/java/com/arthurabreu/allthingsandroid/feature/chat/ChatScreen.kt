package com.arthurabreu.allthingsandroid.feature.chat

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

import com.arthurabreu.allthingsandroid.core.model.ChatMessage

data class ChatState(
    val draft: String = "",
    val messages: List<ChatMessage> = listOf(
        ChatMessage("m0", "bot", "Ktor WebSocket ready (demo).", false),
    ),
    val offline: Boolean = false,
)

class ChatViewModel : ViewModel() {
    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()
    private var seq = 1

    fun onDraft(value: String) { _state.update { it.copy(draft = value) } }

    fun send() {
        val text = _state.value.draft.trim()
        if (text.isEmpty()) return
        val id = "m$seq".also { seq++ }
        val outgoing = ChatMessage(id, "you", text, true, pending = _state.value.offline)
        _state.update {
            val next = it.messages + outgoing
            if (it.offline) it.copy(draft = "", messages = next)
            else it.copy(
                draft = "",
                messages = next + ChatMessage("ack-$id", "bot", "ack: $text", false),
            )
        }
    }

    fun toggleOffline() { _state.update { it.copy(offline = !it.offline) } }
}


@Composable
fun ChatScreen(viewModel: ChatViewModel, onBack: () -> Unit = {}) {
    val state by viewModel.state.collectAsState()
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).testTag("chat-screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Chat", style = MaterialTheme.typography.headlineSmall)

        state.messages.forEach { msg ->
            Text("${msg.author}: ${msg.body}${if (msg.pending) "…" else ""}")
        }
        androidx.compose.material3.OutlinedTextField(
            value = state.draft,
            onValueChange = viewModel::onDraft,
            modifier = Modifier.testTag("chat-input"),
        )
        Button(onClick = viewModel::send, modifier = Modifier.testTag("chat-send")) { Text("Send") }
        if (state.offline) Text("Queued offline", modifier = Modifier.testTag("chat-offline"))
        Button(onClick = viewModel::toggleOffline) { Text("Toggle offline") }

        Button(onClick = onBack) { Text("Back") }
    }
}
