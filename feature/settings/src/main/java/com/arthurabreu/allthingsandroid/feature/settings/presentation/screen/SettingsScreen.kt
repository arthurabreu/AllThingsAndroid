package com.arthurabreu.allthingsandroid.feature.settings.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppCard
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppOutlinedButton
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppScaffold
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppTextButton
import com.arthurabreu.allthingsandroid.core.designsystem.theme.AppTheme
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.feature.settings.presentation.viewmodel.SettingsViewModel
import com.arthurabreu.commonscreens.ui.composables.shared.ConfirmationDialog
import com.arthurabreu.commonscreens.ui.composables.shared.ErrorSnackbarEffect
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val appNavigator: AppNavigator = koinInject()

    var showSignOutConfirm by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    ErrorSnackbarEffect(
        error = uiState.error,
        snackbarHostState = snackbarHostState,
        onClear = viewModel::clearError,
    )

    AppScaffold(
        title = "Settings",
        onBack = { appNavigator.tryNavigateBack() },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = AppTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
        ) {
            Spacer(modifier = Modifier.height(AppTheme.spacing.small))

            // Account Section
            SectionHeader("Account")
            AppCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
                ) {
                    Text(
                        text = "arthurabreupro@gmail.com",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Your account email",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            // Preferences Section
            SectionHeader("Preferences")
            AppCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
                ) {
                    PreferenceToggle(
                        label = "Dark Theme",
                        description = "Use dark colors",
                        checked = uiState.preferences.darkThemeEnabled,
                        onCheckedChange = { viewModel.toggleDarkTheme(it) },
                    )
                    PreferenceToggle(
                        label = "Notifications",
                        description = "Receive app notifications",
                        checked = uiState.preferences.notificationsEnabled,
                        onCheckedChange = { viewModel.toggleNotifications(it) },
                    )
                }
            }

            // About Section
            SectionHeader("About")
            AppCard {
                Text(
                    text = "App Version: 1.0.0",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.spacing.medium),
                    textAlign = TextAlign.Center,
                )
            }

            // Danger Zone
            SectionHeader("Danger Zone", color = MaterialTheme.colorScheme.error)
            AppCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
                ) {
                    AppOutlinedButton(
                        text = "Sign Out",
                        onClick = { showSignOutConfirm = true },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    AppTextButton(
                        text = "Delete Account",
                        onClick = { showDeleteConfirm = true },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppTheme.spacing.large))
        }
    }

    if (showSignOutConfirm) {
        ConfirmationDialog(
            title = "Sign Out",
            text = "Are you sure you want to sign out?",
            confirmLabel = "Sign Out",
            onConfirm = {
                viewModel.signOut()
                showSignOutConfirm = false
            },
            onDismiss = { showSignOutConfirm = false },
        )
    }

    if (showDeleteConfirm) {
        ConfirmationDialog(
            title = "Delete Account",
            text = "This action cannot be undone. Are you sure you want to delete your account?",
            confirmLabel = "Delete",
            confirmColor = MaterialTheme.colorScheme.error,
            onConfirm = {
                viewModel.deleteAccount()
                showDeleteConfirm = false
            },
            onDismiss = { showDeleteConfirm = false },
        )
    }
}

@Composable
private fun SectionHeader(
    text: String,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = color,
        modifier = Modifier.padding(top = AppTheme.spacing.small),
    )
}

@Composable
private fun PreferenceToggle(
    label: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = description,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
