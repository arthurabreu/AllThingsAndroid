package com.arthurabreu.allthingsandroid.ui.screen.buggy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arthurabreu.allthingsandroid.ui.viewmodel.buggy.BuggyViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuggyScreen(
    viewModel: BuggyViewModel = koinViewModel()
) {
    val items by viewModel.items.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Screen with Bugs") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onBackClick() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Section for Simple Bug
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "1. Simple Bug: State Mismanagement",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "This counter uses a plain variable. Pressing the button will update it in memory, but the UI won't react.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(onClick = { viewModel.incrementCounter() }) {
                        Text("Increment Counter")
                    }
                    Text(
                        text = "Count: ${viewModel.simpleCounter}",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

            // Section for Complex Bug
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "2. Complex Bug: Race Condition",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "This button triggers multiple threads modifying a shared list and updating a StateFlow simultaneously. It will likely throw a ConcurrentModificationException.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Button(onClick = { viewModel.triggerComplexBug() }) {
                    Text("Trigger Concurrent Updates")
                }
                
                Text(
                    text = "Loaded items count: ${items.size}",
                    style = MaterialTheme.typography.bodyLarge
                )
                
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(items) { item ->
                        Text(text = item, modifier = Modifier.padding(4.dp))
                    }
                }
            }
        }
    }
}
