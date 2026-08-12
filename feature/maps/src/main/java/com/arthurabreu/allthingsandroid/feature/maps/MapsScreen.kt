package com.arthurabreu.allthingsandroid.feature.maps

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

import com.arthurabreu.allthingsandroid.core.model.MapPin

data class MapsState(
    val pins: List<MapPin> = listOf(
        MapPin("brewery", "Brewery", -23.5505, -46.6333),
        MapPin("taproom", "Taproom", -23.5614, -46.6558),
        MapPin("me", "You", -23.555, -46.64),
    ),
    val showRoute: Boolean = false,
)

class MapsViewModel : ViewModel() {
    private val _state = MutableStateFlow(MapsState())
    val state: StateFlow<MapsState> = _state.asStateFlow()
    fun selectRoute() { _state.update { it.copy(showRoute = true) } }
}


@Composable
fun MapsScreen(viewModel: MapsViewModel, onBack: () -> Unit = {}) {
    val state by viewModel.state.collectAsState()
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).testTag("maps-screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Maps", style = MaterialTheme.typography.headlineSmall)

        Text("Demo map — live flavor uses Maps Compose when MAPS_API_KEY is set.")
        state.pins.forEach { pin ->
            Text("${pin.title} (${pin.lat}, ${pin.lng})", modifier = Modifier.testTag("pin-${pin.id}"))
        }
        Button(onClick = viewModel::selectRoute) { Text("Show polyline") }
        if (state.showRoute) Text("Polyline: brewery -> taproom", modifier = Modifier.testTag("polyline"))

        Button(onClick = onBack) { Text("Back") }
    }
}
