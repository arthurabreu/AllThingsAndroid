package com.arthurabreu.allthingsandroid.feature.persistence

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

import com.arthurabreu.allthingsandroid.core.domain.SchemaStory
import com.arthurabreu.allthingsandroid.core.model.SchemaInfo

data class PersistenceState(
    val versions: List<SchemaInfo> = SchemaStory.versions(),
    val currentVersion: Int = 3,
    val notes: List<String> = emptyList(),
)

class PersistenceViewModel : ViewModel() {
    private val _state = MutableStateFlow(PersistenceState())
    val state: StateFlow<PersistenceState> = _state.asStateFlow()

    fun seedNote() {
        _state.update {
            it.copy(notes = it.notes + "note-${it.notes.size + 1} @ v${it.currentVersion}")
        }
    }
}


@Composable
fun PersistenceScreen(viewModel: PersistenceViewModel, onBack: () -> Unit = {}) {
    val state by viewModel.state.collectAsState()
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).testTag("persistence-screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Persistence", style = MaterialTheme.typography.headlineSmall)

        state.versions.forEach { info ->
            Text("v${info.version} · ${info.migrationKind} · ${info.description}")
        }
        Text("Current schema: v${state.currentVersion}", modifier = Modifier.testTag("schema-version"))
        Button(onClick = viewModel::seedNote) { Text("Insert note") }
        state.notes.forEach { Text(it) }

        Button(onClick = onBack) { Text("Back") }
    }
}
