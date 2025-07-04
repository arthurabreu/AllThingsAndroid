package com.arthurabreu.allthingsandroid.ui.states

import com.arthurabreu.allthingsandroid.domain.model.DownloadData

sealed class DownloadState {
    data object Idle : DownloadState()
    data class Progress(val percentage: Float) : DownloadState()
    data class Success(val data: List<DownloadData>) : DownloadState()
    data class Error(val exception: Throwable) : DownloadState()
}