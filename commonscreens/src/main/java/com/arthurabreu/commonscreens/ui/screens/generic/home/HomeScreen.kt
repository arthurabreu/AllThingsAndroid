package com.arthurabreu.commonscreens.ui.screens.generic.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Api
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartButton
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arthurabreu.allthingsandroid.core.designsystem.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onButtonsClick: () -> Unit,
    onListsClick: () -> Unit,
    onLoginsClick: () -> Unit,
    onLoginFakeClick: () -> Unit,
    onTextFieldsClick: () -> Unit,
    onApiShowcaseClick: () -> Unit,
    onMeditationUiClick: () -> Unit,
    onCalculatorUiClick: () -> Unit,
    onSolidUiClick: () -> Unit,
    onDesignPrincipleUiClick: () -> Unit,
    onOlympicsUiClick: () -> Unit,
    onFeedClick: () -> Unit,
    onMapsClick: () -> Unit,
    onAuthClick: () -> Unit,
    onCloudDbClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onPlayerClick: () -> Unit,
) {
    val sections = listOf(
        HomeMenuSection(
            title = "Core Features",
            items = listOf(
                HomeMenuItem(Icons.Filled.Person, "Profile", onProfileClick),
                HomeMenuItem(Icons.Filled.Settings, "Settings", onSettingsClick),
                HomeMenuItem(Icons.Filled.Download, "Downloads", onDownloadClick),
                HomeMenuItem(Icons.Filled.Notifications, "Notifications", onNotificationsClick),
                HomeMenuItem(Icons.Filled.PlayCircle, "Player", onPlayerClick),
                HomeMenuItem(Icons.AutoMirrored.Filled.Login, "Auth", onAuthClick),
                HomeMenuItem(Icons.Filled.DynamicFeed, "Feed", onFeedClick),
                HomeMenuItem(Icons.Filled.Map, "Maps", onMapsClick),
                HomeMenuItem(Icons.Filled.CloudQueue, "Cloud DB", onCloudDbClick),
            ),
        ),
        HomeMenuSection(
            title = "UI Component Gallery",
            items = listOf(
                HomeMenuItem(Icons.Filled.SmartButton, "Buttons", onButtonsClick),
                HomeMenuItem(Icons.AutoMirrored.Filled.FormatListBulleted, "Lists", onListsClick),
                HomeMenuItem(Icons.Filled.TextFields, "Text Fields", onTextFieldsClick),
                HomeMenuItem(Icons.AutoMirrored.Filled.Login, "Login", onLoginsClick),
                HomeMenuItem(Icons.AutoMirrored.Filled.Login, "Login (Fake)", onLoginFakeClick),
            ),
        ),
        HomeMenuSection(
            title = "Architecture & Patterns",
            items = listOf(
                HomeMenuItem(Icons.Filled.Api, "API Showcase", onApiShowcaseClick),
                HomeMenuItem(Icons.Filled.SelfImprovement, "Meditation", onMeditationUiClick),
                HomeMenuItem(Icons.Filled.Calculate, "Calculator", onCalculatorUiClick),
                HomeMenuItem(Icons.Filled.AccountTree, "SOLID", onSolidUiClick),
                HomeMenuItem(Icons.Filled.DesignServices, "Design Principles", onDesignPrincipleUiClick),
                HomeMenuItem(Icons.Filled.EmojiEvents, "Olympics", onOlympicsUiClick),
            ),
        ),
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("AllThingsAndroid", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(
                horizontal = AppTheme.spacing.medium,
                vertical = AppTheme.spacing.small,
            ),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.large),
        ) {
            item {
                Text(
                    text = "A showcase of modern Android architecture",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            sections.forEach { section ->
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small)) {
                        Text(
                            text = section.title.uppercase(),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small)) {
                            section.items.forEach { menuItem ->
                                HomeMenuItemRow(item = menuItem)
                            }
                        }
                    }
                }
            }
        }
    }
}
