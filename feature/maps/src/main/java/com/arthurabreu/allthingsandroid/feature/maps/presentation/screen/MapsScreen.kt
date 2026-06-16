package com.arthurabreu.allthingsandroid.feature.maps.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppScaffold
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.feature.maps.R
import com.arthurabreu.allthingsandroid.feature.maps.presentation.components.PoiBottomSheet
import com.arthurabreu.allthingsandroid.feature.maps.presentation.viewmodel.MapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun MapsScreen(viewModel: MapsViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState()
    val appNavigator: AppNavigator = koinInject()

    LaunchedEffect(uiState.cameraTarget, uiState.cameraZoom) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(uiState.cameraTarget, uiState.cameraZoom),
        )
    }

    AppScaffold(title = "Maps", onBack = { appNavigator.tryNavigateBack() }) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style),
                ),
            ) {
                Clustering(
                    items = uiState.pois,
                    onClusterItemClick = { poi ->
                        viewModel.onMarkerClick(poi)
                        false
                    },
                )
            }

            FloatingActionButton(
                onClick = viewModel::recenter,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(PaddingValues(end = 16.dp, bottom = 16.dp)),
            ) {
                Icon(Icons.Default.MyLocation, contentDescription = "Recenter")
            }
        }

        uiState.selectedPoi?.let { poi ->
            PoiBottomSheet(poi = poi, onDismiss = viewModel::dismissBottomSheet)
        }
    }
}
