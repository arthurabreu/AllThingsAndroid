package com.arthurabreu.commonscreens.ui.screens.generic.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
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
) {
    Column {
        Button(
            onClick = { onProfileClick() }
        ) {
            Text(stringResource(R.string.go_to_profile))
        }
        Button(
            onClick = { onSettingsClick() }
        ) {
            Text(stringResource(R.string.go_to_settings))
        }
        Button(
            onClick = { onDownloadClick() }
        ) {
            Text(stringResource(R.string.go_to_downloads))
        }
        Button(
            onClick = { onButtonsClick() }
        ) {
            Text(stringResource(R.string.go_to_buttons_example))
        }
        Button(
            onClick = { onListsClick() }
        ) {
            Text(stringResource(R.string.go_to_lists_example))
        }
        Button(
            onClick = { onLoginsClick() }
        ) {
            Text(stringResource(R.string.go_to_logins_example))
        }
        Button(
            onClick = { onLoginsClick() }
        ) {
            Text(stringResource(R.string.go_to_logins_fake_example))
        }
        Button(
            onClick = { onTextFieldsClick() }
        ) {
            Text(stringResource(R.string.go_to_text_fields_example))
        }
        Button(
            onClick = { onApiShowcaseClick() }
        ) {
            Text(stringResource(R.string.go_to_api_showcase_example))
        }

    }
}