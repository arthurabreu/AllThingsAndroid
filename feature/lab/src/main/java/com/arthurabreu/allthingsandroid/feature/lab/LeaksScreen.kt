package com.arthurabreu.allthingsandroid.feature.lab

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

data class LeaksState(val armed: Boolean = false)

class LeaksViewModel : ViewModel() {
    private val _state = MutableStateFlow(LeaksState())
    val state: StateFlow<LeaksState> = _state.asStateFlow()

    fun toggleLeak() {
        _state.update { it.copy(armed = !it.armed) }
        if (_state.value.armed) LeakHolder.ref = this else LeakHolder.ref = null
    }
}

object LeakHolder {
    var ref: Any? = null
}


@Composable
fun LeaksScreen(viewModel: LeaksViewModel, onBack: () -> Unit = {}) {
    val state by viewModel.state.collectAsState()
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).testTag("leaks-screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Leaks", style = MaterialTheme.typography.headlineSmall)

        Text("LeakCanary is on debug builds. This button keeps a static reference on purpose.")
        Button(onClick = viewModel::toggleLeak, modifier = Modifier.testTag("leak-toggle")) {
            Text(if (state.armed) "Disarm leak" else "Arm intentional leak")
        }
        Text(if (state.armed) "LEAK ARMED" else "safe", modifier = Modifier.testTag("leak-state"))

        Button(onClick = onBack) { Text("Back") }
    }
}
