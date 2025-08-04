package com.arthurabreu.commonscreens.ui.screens.generic.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arthurabreu.allthingsandroid.commonscreens.R

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
) {
    val menuButtons = listOf(
        MenuButtonInfo(R.string.go_to_profile, onProfileClick),
        MenuButtonInfo(R.string.go_to_settings, onSettingsClick),
        MenuButtonInfo(R.string.go_to_downloads, onDownloadClick),
        MenuButtonInfo(R.string.go_to_buttons_example, onButtonsClick),
        MenuButtonInfo(R.string.go_to_lists_example, onListsClick),
        MenuButtonInfo(R.string.go_to_logins_example, onLoginsClick),
        MenuButtonInfo(R.string.go_to_logins_fake_example, onLoginFakeClick),
        MenuButtonInfo(R.string.go_to_text_fields_example, onTextFieldsClick),
        MenuButtonInfo(R.string.go_to_api_showcase_example, onApiShowcaseClick),
        MenuButtonInfo(R.string.go_to_meditation_ui_example, onMeditationUiClick),
        MenuButtonInfo(R.string.go_to_calculator_example, onCalculatorUiClick),
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        item {
            menuButtons.forEach { buttonInfo ->
                MenuButton(
                    text = stringResource(id = buttonInfo.textResId),
                    onClick = buttonInfo.onClick
                )
            }
        }
    }
}