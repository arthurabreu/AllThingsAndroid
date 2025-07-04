package com.arthurabreu.commonscreens.ui.screens.textfields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arthurabreu.commonscreens.ui.composables.textfields.AllTextFieldsComposable
import com.arthurabreu.commonscreens.ui.previewdata.textfields.TextFieldStatesProvider

@Composable
fun AllTextFieldsScreen(modifier: Modifier = Modifier) {
    val textFieldStates = TextFieldStatesProvider.getAllTextFieldStates()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(textFieldStates) { state ->
            AllTextFieldsComposable(state = state)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AllTextFieldsScreenPreview() {
    MaterialTheme {
        AllTextFieldsScreen()
    }
}