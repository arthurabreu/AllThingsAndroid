package com.arthurabreu.allthingsandroid.feature.notifications.presentation.screen

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppScaffold
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.feature.notifications.presentation.viewmodel.NotificationsViewModel
import com.arthurabreu.commonscreens.ui.composables.shared.CenteredContentColumn
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(viewModel: NotificationsViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* handled by OS */ }
    val appNavigator: AppNavigator = koinInject()

    AppScaffold(title = "Notifications", onBack = { appNavigator.tryNavigateBack() }) { padding ->
        CenteredContentColumn(padding = padding, contentPadding = 24.dp) {
            Text("FCM Token", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                text = uiState.fcmToken ?: "No token yet (launch app to generate)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(Modifier.height(32.dp))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Button(
                    onClick = { permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) },
                    modifier = Modifier.fillMaxWidth(),
                ) { Text("Request Notification Permission") }
                Spacer(Modifier.height(12.dp))
            }

            Button(
                onClick = { viewModel.scheduleLocalNotification(5L) },
                modifier = Modifier.fillMaxWidth(),
            ) { Text("Schedule Local Notification (5s)") }
        }
    }
}
