package com.arthurabreu.commonscreens.ui.screens.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arthurabreu.commonscreens.ui.previewdata.lists.ListStatesProvider
import com.arthurabreu.commonscreens.ui.composables.lists.ListsComposable

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
                title = { Text("Galeria de Estilos de Lista") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Aplica o padding do Scaffold
                .padding(horizontal = 16.dp), // Padding lateral para todo o conteúdo da LazyColumn
            contentPadding = PaddingValues(vertical = 24.dp), // Padding no topo e base da LazyColumn
            verticalArrangement = Arrangement.spacedBy(32.dp) // Espaço vertical entre cada bloco ListsComposable
        ) {
            itemsIndexed(listStatesToDisplay) { index, listState ->
                // Cada ListsComposable é renderizada aqui
                // Você pode adicionar um título geral para cada seção se quiser,
                // mas o listState.listTitle já deve servir para isso.
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        // Limita a altura de cada exemplo para melhor visualização na galeria
                        .heightIn(min = 100.dp, max = 400.dp)
                ) {
                    ListsComposable(state = listState)
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