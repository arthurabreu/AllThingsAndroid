package com.arthurabreu.allthingsandroid.feature.lists

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

import com.arthurabreu.allthingsandroid.core.domain.ListFilter
import com.arthurabreu.allthingsandroid.core.domain.SeedRows
import com.arthurabreu.allthingsandroid.core.model.ListRow

data class ListsState(
    val query: String = "",
    val visible: List<ListRow> = emptyList(),
    val error: String? = null,
)

class ListsViewModel(
    private val filter: ListFilter = ListFilter(),
    private val seed: List<ListRow> = SeedRows.generate(),
) : ViewModel() {
    private val _state = MutableStateFlow(ListsState(visible = seed))
    val state: StateFlow<ListsState> = _state.asStateFlow()

    fun onQuery(value: String) {
        _state.update { it.copy(query = value, visible = filter.apply(seed, value), error = null) }
    }

    fun fail() { _state.update { it.copy(error = "Network unavailable", visible = emptyList()) } }
    fun retry() { onQuery(_state.value.query) }
}


@Composable
fun ListsScreen(viewModel: ListsViewModel, onBack: () -> Unit = {}) {
    val state by viewModel.state.collectAsState()
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).testTag("lists-screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Lists", style = MaterialTheme.typography.headlineSmall)

        androidx.compose.material3.OutlinedTextField(
            value = state.query,
            onValueChange = viewModel::onQuery,
            label = { Text("Search") },
            modifier = Modifier.testTag("lists-search"),
        )
        if (state.error != null) {
            Text(state.error!!, modifier = Modifier.testTag("lists-error"))
            Button(onClick = viewModel::retry) { Text("Retry") }
        } else if (state.visible.isEmpty()) {
            Text("Empty", modifier = Modifier.testTag("lists-empty"))
        } else {
            state.visible.forEach { Text("${it.title} — ${it.body}") }
        }
        Button(onClick = viewModel::fail) { Text("Simulate error") }

        Button(onClick = onBack) { Text("Back") }
    }
}
