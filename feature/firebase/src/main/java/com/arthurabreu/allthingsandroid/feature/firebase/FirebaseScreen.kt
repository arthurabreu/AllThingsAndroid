package com.arthurabreu.allthingsandroid.feature.firebase

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

import com.arthurabreu.allthingsandroid.core.model.FirebaseSnapshot

data class FirebaseState(
    val signedIn: Boolean = false,
    val userLabel: String = "anonymous",
    val remoteBanner: String = "Welcome (Remote Config demo)",
    val fcmToken: String = "demo-fcm-token",
    val notes: List<String> = emptyList(),
    val lastUpload: String? = null,
    val crashed: Boolean = false,
)

class FirebaseViewModel : ViewModel() {
    private val _state = MutableStateFlow(FirebaseState())
    val state: StateFlow<FirebaseState> = _state.asStateFlow()

    fun toggleAuth() {
        _state.update {
            val signed = !it.signedIn
            it.copy(signedIn = signed, userLabel = if (signed) "demo@local" else "anonymous")
        }
    }

    fun addNote() { _state.update { it.copy(notes = it.notes + "note-${it.notes.size + 1}") } }
    fun upload() { _state.update { it.copy(lastUpload = "uploaded://local/evidence.jpg") } }
    fun crash() { _state.update { it.copy(crashed = true) } }
    fun snapshot(): FirebaseSnapshot = state.value.let {
        FirebaseSnapshot(it.signedIn, it.userLabel, it.remoteBanner, it.fcmToken, it.notes, it.lastUpload)
    }
}


@Composable
fun FirebaseScreen(viewModel: FirebaseViewModel, onBack: () -> Unit = {}) {
    val state by viewModel.state.collectAsState()
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).testTag("firebase-screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Firebase", style = MaterialTheme.typography.headlineSmall)

        Text(if (state.signedIn) "Signed in as ${state.userLabel}" else "Signed out", modifier = Modifier.testTag("auth-state"))
        Text(state.remoteBanner, modifier = Modifier.testTag("rc-banner"))
        Text("FCM: ${state.fcmToken}")
        state.notes.forEach { Text(it) }
        Button(onClick = viewModel::toggleAuth) { Text("Toggle auth") }
        Button(onClick = viewModel::addNote) { Text("Add Firestore note") }
        Button(onClick = viewModel::upload) { Text("Upload to Storage") }
        state.lastUpload?.let { Text(it, modifier = Modifier.testTag("upload-status")) }
        Button(onClick = viewModel::crash) { Text("Crashlytics test") }
        if (state.crashed) Text("Crash recorded (demo)", modifier = Modifier.testTag("crash-flag"))

        Button(onClick = onBack) { Text("Back") }
    }
}
