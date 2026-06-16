package com.arthurabreu.allthingsandroid.feature.maps.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.feature.maps.data.PoiRepository
import com.arthurabreu.allthingsandroid.feature.maps.domain.model.MapPoi
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MapsUiState(
    val pois: List<MapPoi> = emptyList(),
    val selectedPoi: MapPoi? = null,
    val cameraTarget: LatLng = LatLng(-22.9068, -43.1729), // Rio de Janeiro center
    val cameraZoom: Float = 11f,
)

class MapsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MapsUiState(pois = PoiRepository.getSamplePois()))
    val uiState: StateFlow<MapsUiState> = _uiState.asStateFlow()

    fun onMarkerClick(poi: MapPoi) {
        _uiState.update { it.copy(selectedPoi = poi, cameraTarget = poi.latLng, cameraZoom = 14f) }
    }

    fun dismissBottomSheet() {
        _uiState.update { it.copy(selectedPoi = null) }
    }

    fun recenter() {
        _uiState.update {
            it.copy(
                selectedPoi = null,
                cameraTarget = LatLng(-22.9068, -43.1729),
                cameraZoom = 11f,
            )
        }
    }
}
