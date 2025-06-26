package com.arthurabreu.allthingsandroid.ui.screen.download

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arthurabreu.allthingsandroid.ui.states.DownloadState
import com.arthurabreu.allthingsandroid.ui.viewmodel.download.DownloadViewModel
import com.arthurabreu.commonscreens.ui.screens.download.DownloadProgress
import com.arthurabreu.commonscreens.ui.screens.download.StartDownload
import com.arthurabreu.commonscreens.utils.ColorPickerDialog
import org.koin.androidx.compose.koinViewModel


@Composable
fun DownloadScreen(
    viewModel: DownloadViewModel = koinViewModel()
) {
    val downloadState by viewModel.downloadState.collectAsState()
    val progressColor by viewModel.progressColor.collectAsState()
    val showDialog by viewModel.showColorPicker.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val state = downloadState) {
            DownloadState.Idle -> {
                StartDownload(
                    startDownload = { viewModel.startDownload() },
                    onColorPick = { viewModel.showColorPicker() }
                )
            }

            is DownloadState.Progress -> {
                DownloadProgress(
                    progress = state.percentage,
                    progressColor = progressColor
                )
            }

            is DownloadState.Success -> {
                StartDownload(
                    isStartDownload = false,
                    startDownload = { viewModel.startDownload() },
                    onColorPick = { viewModel.showColorPicker() }
                )
                // Display your data here
            }

            is DownloadState.Error -> {
                Text("Error: ${state.exception.localizedMessage}", color = Color.Red)
                Button(onClick = { viewModel.startDownload() }) {
                    Text("Retry")
                }
            }
        }

        if (showDialog) {
            ColorPickerDialog(
                onColorSelected = { color ->
                    viewModel.saveColor(color)
                    viewModel.hideColorPicker()
                },
                onDismiss = { viewModel.hideColorPicker() }
            )
        }
    }
}

