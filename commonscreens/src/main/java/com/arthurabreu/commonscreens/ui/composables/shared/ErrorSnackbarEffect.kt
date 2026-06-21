package com.arthurabreu.commonscreens.ui.composables.shared

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ErrorSnackbarEffect(
    error: String?,
    snackbarHostState: SnackbarHostState,
    onClear: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(error) {
        error?.let {
            scope.launch { snackbarHostState.showSnackbar(it) }
            onClear()
        }
    }
}
