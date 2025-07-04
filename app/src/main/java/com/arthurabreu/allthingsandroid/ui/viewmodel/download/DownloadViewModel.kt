package com.arthurabreu.allthingsandroid.ui.viewmodel.download

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurabreu.allthingsandroid.ui.states.DownloadState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DownloadViewModel(
    //private val getLargeDataUseCase: GetLargeDataUseCase,
) : ViewModel() {
    private val _downloadState = MutableStateFlow<DownloadState>(DownloadState.Idle)
    val downloadState: StateFlow<DownloadState> = _downloadState

    private val _progressColor = MutableStateFlow(Color.Blue)
    val progressColor: StateFlow<Color> = _progressColor

    private val _showColorPicker = MutableStateFlow(false)
    val showColorPicker: StateFlow<Boolean> = _showColorPicker

    fun startDownload() {
        viewModelScope.launch {
            // auto fill download values
            for (i in 0..100) {
                _downloadState.value = DownloadState.Progress(i.toFloat())
                delay(50)
            }

            // Manually test and set download values
//            _downloadState.value = DownloadState.Progress(0f)
//            delay(500)
//            _downloadState.value = DownloadState.Progress(25f)
//            delay(1000)
//            _downloadState.value = DownloadState.Progress(50f)
//            delay(1000)
//            _downloadState.value = DownloadState.Progress(75f)
//            delay(1000)
//            _downloadState.value = DownloadState.Progress(100f)

            _downloadState.value = DownloadState.Success(listOf())
//            getLargeDataUseCase().collect { state ->
//                _downloadState.value = state
//            }
        }
    }

    fun saveColor(color: Color) {
        viewModelScope.launch {
            _progressColor.value = color
        }
    }

    fun showColorPicker() {
        _showColorPicker.value = true
    }

    fun hideColorPicker() {
        _showColorPicker.value = false
    }
}