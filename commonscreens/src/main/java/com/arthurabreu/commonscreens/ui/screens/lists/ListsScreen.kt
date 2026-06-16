package com.arthurabreu.commonscreens.ui.screens.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppCard
import com.arthurabreu.allthingsandroid.core.designsystem.theme.AppTheme
import com.arthurabreu.commonscreens.ui.composables.lists.ListsComposable
import com.arthurabreu.commonscreens.ui.previewdata.lists.ListStatesProvider

/**
 * Tela de exemplo que demonstra vários estilos de lista usando ListsComposable,
 * buscando os dados e configurações de ListStatesProvider.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsScreen() {
    val listStatesToDisplay = ListStatesProvider.getAllListStates()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("List Style Gallery", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = AppTheme.spacing.medium),
            contentPadding = PaddingValues(vertical = AppTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
        ) {
            itemsIndexed(listStatesToDisplay) { index, listState ->
                AppCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(AppTheme.spacing.medium),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = CircleShape,
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "%02d".format(index + 1),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }
                        Text(
                            text = listState.listTitle ?: "Untitled",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = AppTheme.spacing.medium),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 320.dp)
                            .clip(MaterialTheme.shapes.large),
                    ) {
                        ListsComposable(state = listState)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 380, heightDp = 1200)
@Composable
private fun ListsExampleScreenFullPreview() {
    MaterialTheme {
        ListsScreen()
    }
}
