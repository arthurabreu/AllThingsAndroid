package com.arthurabreu.allthingsandroid.ui.viewmodel.download

import androidx.compose.ui.graphics.Color
import com.arthurabreu.allthingsandroid.ui.states.DownloadState

data class DownloadUiState(
    val downloadState: DownloadState = DownloadState.Idle,
    val progressColor: Color = Color.Blue,
    val showColorPicker: Boolean = false,
)
