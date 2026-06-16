package com.arthurabreu.allthingsandroid.feature.feed.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState

@Composable
fun FeedLoadStateFooter(
    loadState: LoadState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
        when (loadState) {
            is LoadState.Loading -> CircularProgressIndicator()
            is LoadState.Error -> Button(onClick = onRetry) { Text("Retry") }
            is LoadState.NotLoading -> Unit
        }
    }
}
