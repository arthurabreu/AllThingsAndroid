package com.arthurabreu.commonscreens.ui.screens.buttons

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arthurabreu.commonscreens.ui.composables.buttons.ButtonComposable
import com.arthurabreu.commonscreens.ui.previewdata.buttons.ButtonStatesProvider

/*
    * Example of how to create and configure button states for use in a Composable.
 */
@Composable
fun ButtonScreen() {
    val buttonStates = ButtonStatesProvider.getAllButtonStates()
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            buttonStates.forEach { state ->
                ButtonComposable(state = state)
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
@Preview
private fun ButtonExampleScreenPreview() {
    MaterialTheme {
        ButtonScreen()
    }
}