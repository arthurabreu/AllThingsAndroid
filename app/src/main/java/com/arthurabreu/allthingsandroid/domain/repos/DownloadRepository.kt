package com.arthurabreu.allthingsandroid.domain.repos

import com.arthurabreu.allthingsandroid.ui.states.DownloadState
import kotlinx.coroutines.flow.Flow

interface DownloadRepository {
    suspend fun fetchLargeData(): Flow<DownloadState>
}