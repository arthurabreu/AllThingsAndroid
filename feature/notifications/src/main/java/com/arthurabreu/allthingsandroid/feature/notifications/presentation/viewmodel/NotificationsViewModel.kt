package com.arthurabreu.allthingsandroid.feature.notifications.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.arthurabreu.allthingsandroid.feature.notifications.service.FcmTokenStore
import com.arthurabreu.allthingsandroid.feature.notifications.worker.LocalNotificationWorker
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.concurrent.TimeUnit

class NotificationsViewModel(
    private val tokenStore: FcmTokenStore,
    private val context: Context,
) : ViewModel() {

    val uiState: StateFlow<NotificationsUiState> = tokenStore.tokenFlow
        .map { token -> NotificationsUiState(fcmToken = token) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), NotificationsUiState())

    fun scheduleLocalNotification(delaySeconds: Long = 5L) {
        val data = workDataOf(
            "title" to "AllThingsAndroid",
            "body" to "Local notification from WorkManager 🎉",
        )
        val request = OneTimeWorkRequestBuilder<LocalNotificationWorker>()
            .setInitialDelay(delaySeconds, TimeUnit.SECONDS)
            .setInputData(data)
            .build()
        WorkManager.getInstance(context).enqueue(request)
    }
}
