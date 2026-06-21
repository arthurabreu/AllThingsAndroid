package com.arthurabreu.allthingsandroid.ui.viewmodel.download

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurabreu.allthingsandroid.ui.states.DownloadState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DownloadViewModel(
    //private val getLargeDataUseCase: GetLargeDataUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DownloadUiState())
    val uiState: StateFlow<DownloadUiState> = _uiState.asStateFlow()

    fun startDownload() {
        viewModelScope.launch {
            for (i in 0..100) {
                _uiState.value = _uiState.value.copy(downloadState = DownloadState.Progress(i.toFloat()))
                delay(50)
            }
            _uiState.value = _uiState.value.copy(downloadState = DownloadState.Success(listOf()))
        }
    }

    fun saveColor(color: Color) {
        _uiState.value = _uiState.value.copy(progressColor = color)
    }

    fun showColorPicker() {
        _uiState.value = _uiState.value.copy(showColorPicker = true)
    }

    fun hideColorPicker() {
        _uiState.value = _uiState.value.copy(showColorPicker = false)
    }
}