package com.arthurabreu.allthingsandroid.feature.profile.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppButton
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppCard
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppLoadingIndicator
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppScaffold
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppTextField
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppTextButton
import com.arthurabreu.allthingsandroid.core.designsystem.theme.AppTheme
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.feature.profile.presentation.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    @Suppress("UNUSED_PARAMETER") userId: String = "",  // Currently ignored; single fake "current user"
    viewModel: ProfileViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val appNavigator: AppNavigator = koinInject()

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            scope.launch { snackbarHostState.showSnackbar(it) }
            viewModel.clearError()
        }
    }

    AppScaffold(
        title = "Profile",
        onBack = { appNavigator.tryNavigateBack() },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        if (uiState.isLoading) {
            AppLoadingIndicator(modifier = Modifier.fillMaxSize())
        } else {
            uiState.profile?.let { profile ->
                ProfileContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState()),
                    profile = profile,
                    isEditing = uiState.isEditing,
                    onStartEdit = { viewModel.startEdit() },
                    onCancelEdit = { viewModel.cancelEdit() },
                    onSaveEdit = { name, bio -> viewModel.saveEdit(name, bio) },
                )
            }
        }
    }
}

@Composable
private fun ProfileContent(
    modifier: Modifier = Modifier,
    profile: com.arthurabreu.allthingsandroid.feature.profile.domain.model.Profile,
    isEditing: Boolean = false,
    onStartEdit: () -> Unit = {},
    onCancelEdit: () -> Unit = {},
    onSaveEdit: (String, String) -> Unit = { _, _ -> },
) {

    var editingName by remember(profile) { mutableStateOf(profile.displayName) }
    var editingBio by remember(profile) { mutableStateOf(profile.bio) }

    Column(
        modifier = modifier.padding(horizontal = AppTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(MaterialTheme.colorScheme.surfaceContainerLow, CircleShape)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile picture",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.small))

        // Name & Email Card
        AppCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
            ) {
                if (isEditing) {
                    AppTextField(
                        value = editingName,
                        onValueChange = { editingName = it },
                        label = "Display Name",
                        modifier = Modifier.fillMaxWidth(),
                    )
                } else {
                    Text(
                        text = profile.displayName,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                Text(
                    text = profile.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        // Bio
        AppCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
            ) {
                Text(
                    text = "Bio",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                if (isEditing) {
                    AppTextField(
                        value = editingBio,
                        onValueChange = { editingBio = it },
                        label = "Bio",
                        modifier = Modifier.fillMaxWidth(),
                    )
                } else {
                    Text(
                        text = profile.bio.ifBlank { "(No bio yet)" },
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }

        // Stats
        AppCard {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.medium),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                StatItem("Posts", profile.postsCount)
                StatItem("Followers", profile.followersCount)
                StatItem("Following", profile.followingCount)
            }
        }

        // Join Date
        AppCard {
            Text(
                text = "Joined ${profile.joinDate}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.medium),
                textAlign = TextAlign.Center,
            )
        }

        // Edit / Save / Cancel buttons
        if (isEditing) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
            ) {
                AppButton(
                    text = "Save",
                    onClick = { onSaveEdit(editingName, editingBio) },
                    modifier = Modifier.weight(1f),
                    enabled = editingName.isNotBlank(),
                )
                AppTextButton(
                    text = "Cancel",
                    onClick = {
                        editingName = profile.displayName
                        editingBio = profile.bio
                        onCancelEdit()
                    },
                    modifier = Modifier.weight(1f),
                )
            }
        } else {
            AppButton(
                text = "Edit Profile",
                onClick = onStartEdit,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.large))
    }
}

@Composable
private fun StatItem(label: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
