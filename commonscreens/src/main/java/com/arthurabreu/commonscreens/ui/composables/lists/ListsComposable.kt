package com.arthurabreu.commonscreens.ui.composables.lists

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arthurabreu.commonscreens.domain.model.lists.ListItemData
import com.arthurabreu.commonscreens.ui.state.lists.AllListsState
import com.arthurabreu.commonscreens.ui.style.ListDividerStyle
import com.arthurabreu.commonscreens.ui.style.ListItemStyle

/**
 * A generic and customizable Composable for displaying a list of items with an optional title.
 * The appearance and behavior of the list and its items are controlled by the [state] parameter.
 *
 * @param state The [AllListsState] that defines the content, title, and styling of the list.
 */
@Composable
fun ListsComposable(state: AllListsState) {
    // Column externa para conter o título e a LazyColumn
    Column(
        modifier = Modifier
            .fillMaxSize() // A Column externa preenche o espaço
            .background(state.listBackgroundColor) // O fundo é aplicado à Column externa
    ) {
        state.listTitle?.let { title ->
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge, // Ou outro estilo de sua preferência
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(state.titlePadding) // Use o padding específico do título
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                // Se houver título, a LazyColumn pode precisar de peso para preencher o espaço restante,
                // ou simplesmente deixe-a crescer conforme o conteúdo.
                // Para este caso, vamos deixá-la crescer com o conteúdo, mas sem fillMaxSize
                // pois a Column externa já cuida disso.
                .weight(1f, fill = false) // Opcional: faz a LazyColumn preencher o espaço restante se a Column tiver tamanho fixo.
                // 'fill = false' significa que não vai forçar o preenchimento se o conteúdo for menor.
                .padding(state.listPadding) // Padding para a área dos itens da lista
        ) {
            items(state.items, key = { it.id }) { item ->
                if (state.dividerStyle.showAbove) {
                    HorizontalDivider(
                        modifier = Modifier.padding(state.dividerStyle.padding),
                        thickness = state.dividerStyle.thickness,
                        color = state.dividerStyle.color
                    )
                }

                ListItemComposable(item = item, state = state)

                if (state.dividerStyle.showBelow) {
                    HorizontalDivider(
                        modifier = Modifier.padding(state.dividerStyle.padding),
                        thickness = state.dividerStyle.thickness,
                        color = state.dividerStyle.color
                    )
                }
            }
        }
    }
}

@Composable
fun ListItemComposable(item: ListItemData, state: AllListsState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(state.itemStyle.backgroundColor, state.itemStyle.shape)
            .border(
                border = state.itemStyle.border ?: BorderStroke(0.dp, Color.Transparent),
                shape = state.itemStyle.shape
            )
            .padding(state.itemStyle.padding)
    ) {
        Column {
            Text(
                text = item.text,
                color = state.itemStyle.textColor,
                style = MaterialTheme.typography.bodyLarge
            )
            item.description?.let { desc ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = desc,
                    color = state.itemStyle.textColor.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "List with Title Preview")
@Composable
fun PreviewListsComposableWithTitle() {
    val sampleItems = List(3) { index ->
        ListItemData(
            id = "item_$index",
            text = "Item Preview $index",
            description = "Description for item $index"
        )
    }

    val sampleStateWithTitle = AllListsState(
        items = sampleItems,
        listTitle = "Título da Lista de Exemplo",
        listBackgroundColor = Color.LightGray,
        listPadding = PaddingValues(horizontal = 8.dp),
        titlePadding = PaddingValues(horizontal = 16.dp),
        itemStyle = ListItemStyle(
            textColor = Color.Black,
            backgroundColor = Color.White,
            padding = PaddingValues(16.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ),
        dividerStyle = ListDividerStyle(
            showBelow = true,
            color = Color.DarkGray,
            thickness = 1.dp,
            padding = PaddingValues(horizontal = 16.dp)
        )
    )

    MaterialTheme {
        ListsComposable(state = sampleStateWithTitle)
    }
}

@Preview(showBackground = true, name = "List without Title Preview")
@Composable
fun PreviewListsComposableWithoutTitle() {
    val sampleItems = List(2) { index ->
        ListItemData(id = "item_no_title_$index", text = "Item Sem Título $index")
    }

    val sampleStateWithoutTitle = AllListsState(
        items = sampleItems,
        listTitle = null,
        listBackgroundColor = Color.White,
        itemStyle = ListItemStyle(
            textColor = Color.DarkGray,
            backgroundColor = Color(0xFFEEEEEE),
            padding = PaddingValues(12.dp)
        ),
        dividerStyle = ListDividerStyle(showBelow = true, color = Color.Cyan)
    )
    MaterialTheme {
        ListsComposable(state = sampleStateWithoutTitle)
    }
}