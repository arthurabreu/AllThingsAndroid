package com.arthurabreu.commonscreens.ui.previewdata.lists

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arthurabreu.commonscreens.domain.model.lists.ListItemData
import com.arthurabreu.commonscreens.ui.state.lists.AllListsState
import com.arthurabreu.commonscreens.ui.style.ListDividerStyle
import com.arthurabreu.commonscreens.ui.style.ListItemStyle

object ListStatesProvider {

    private val sampleItemsShort = List(3) { index ->
        ListItemData(
            id = "item_short_${index + 1}",
            text = "Item Curto ${index + 1}",
            description = if (index % 2 == 0) "Desc. opcional" else null
        )
    }
    private val sampleItemsMedium = List(5) { index ->
        ListItemData(
            id = "item_medium_${index + 1}",
            text = "Opção de Menu ${('A' + index)}",
            description = "Detalhes sobre a opção ${('A' + index)}"
        )
    }
    private val sampleItemsLong = List(4) { index ->
        ListItemData(
            id = "item_long_${index + 1}",
            text = "Título Principal do Item Longo ${index + 1}",
            description = "Esta é uma descrição consideravelmente mais longa para o item número ${index + 1}, projetada especificamente para testar como o texto se ajusta e quebra em múltiplas linhas dentro do espaço disponível para cada item da lista."
        )
    }
    private val taskItems = List(4) { index ->
        ListItemData(
            id = "task_${index + 1}",
            text = "Tarefa ${index + 1}: ${if (index % 2 == 0) "Completar relatório" else "Revisar código"}",
            description = if (index % 3 == 0) "Prioridade Alta" else "Prioridade Normal"
        )
    }

    // --- ESTADOS DE LISTA ---

    val basicListWithDividers: AllListsState = AllListsState(
        items = sampleItemsMedium,
        listTitle = "1. Lista Básica com Divisores",
        listBackgroundColor = Color.Companion.White,
        listPadding = PaddingValues(0.dp),
        titlePadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
        itemStyle = ListItemStyle(
            textColor = Color.Companion.Black,
            backgroundColor = Color.Companion.Transparent,
            padding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        ),
        dividerStyle = ListDividerStyle(
            showBelow = true,
            color = Color.Companion.LightGray,
            thickness = 1.dp,
            padding = PaddingValues(horizontal = 16.dp)
        )
    )

    val compactListNoDividers: AllListsState = AllListsState(
        items = sampleItemsShort,
        listTitle = "2. Lista Compacta (Sem Divisores)",
        listBackgroundColor = Color(0xFFF0F0F0), // Cinza claro
        listPadding = PaddingValues(all = 8.dp),
        titlePadding = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp),
        itemStyle = ListItemStyle(
            textColor = Color.Companion.DarkGray,
            backgroundColor = Color.Companion.White,
            padding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            shape = RoundedCornerShape(4.dp)
        ),
        dividerStyle = ListDividerStyle(showBelow = false)
    )

    val roundedItemsList: AllListsState = AllListsState(
        items = sampleItemsShort,
        listTitle = "3. Itens Arredondados e Sombra Suave",
        listBackgroundColor = Color(0xFFE0F2F7), // Azul muito claro
        listPadding = PaddingValues(16.dp),
        titlePadding = PaddingValues(start = 16.dp, top = 16.dp, bottom = 12.dp),
        itemStyle = ListItemStyle(
            textColor = Color(0xFF00796B), // Verde azulado escuro
            backgroundColor = Color.Companion.White,
            padding = PaddingValues(16.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            // Adicionar elevação/sombra aqui exigiria modificar ListItemComposable
            // para aceitar um modifier ou usar um Surface. Por simplicidade, manteremos sem sombra direta no estado.
            border = BorderStroke(1.dp, Color(0xFFB2DFDB)) // Borda suave
        ),
        dividerStyle = ListDividerStyle(showBelow = false)
    )

    val darkThemeList: AllListsState = AllListsState(
        items = sampleItemsMedium,
        listTitle = "4. Exemplo de Tema Escuro",
        listBackgroundColor = Color(0xFF121212),
        listPadding = PaddingValues(horizontal = 8.dp),
        titlePadding = PaddingValues(start = 16.dp, top = 16.dp, bottom = 8.dp),
        itemStyle = ListItemStyle(
            textColor = Color.Companion.White,
            backgroundColor = Color(0xFF1E1E1E),
            padding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp),
            border = BorderStroke(1.dp, Color(0xFF2C2C2C))
        ),
        dividerStyle = ListDividerStyle(
            showBelow = true,
            color = Color(0xFF3A3A3A), // Divisor escuro
            thickness = 1.dp,
            padding = PaddingValues(horizontal = 16.dp)
        )
    )

    val insetDividersList: AllListsState = AllListsState(
        items = sampleItemsMedium,
        listTitle = "5. Divisores Recuados (Inset)",
        listBackgroundColor = Color.Companion.White,
        titlePadding = PaddingValues(16.dp),
        itemStyle = ListItemStyle(
            textColor = Color.Companion.Black,
            padding = PaddingValues(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 14.dp)
        ),
        dividerStyle = ListDividerStyle(
            showBelow = true,
            color = Color.Companion.Gray,
            thickness = 1.dp,
            padding = PaddingValues(start = 72.dp, end = 16.dp) // Recuo significativo no início
        )
    )

    val cutCornerItemsList: AllListsState = AllListsState(
        items = taskItems.take(2),
        listTitle = "6. Itens com Cantos Cortados",
        listBackgroundColor = Color(0xFFFFF8E1), // Amarelo claro
        listPadding = PaddingValues(12.dp),
        titlePadding = PaddingValues(16.dp),
        itemStyle = ListItemStyle(
            textColor = Color(0xFFC07B00), // Laranja escuro
            backgroundColor = Color.Companion.White,
            padding = PaddingValues(20.dp),
            shape = CutCornerShape(topStart = 20.dp, bottomEnd = 20.dp),
            border = BorderStroke(2.dp, Color(0xFFFFD54F)) // Amarelo
        ),
        dividerStyle = ListDividerStyle(showBelow = false)
    )

    val fullWidthDividersThick: AllListsState = AllListsState(
        items = sampleItemsShort,
        listTitle = "7. Divisores Largos e Espessos",
        listPadding = PaddingValues(vertical = 8.dp),
        titlePadding = PaddingValues(16.dp),
        itemStyle = ListItemStyle(
            textColor = Color.Companion.Black,
            padding = PaddingValues(all = 16.dp)
        ),
        dividerStyle = ListDividerStyle(
            showBelow = true,
            color = Color.Companion.Black,
            thickness = 2.dp, // Divisor mais espesso
            padding = PaddingValues(0.dp) // Sem padding para largura total
        )
    )

    val itemsWithVisibleBorders: AllListsState = AllListsState(
        items = sampleItemsShort,
        listTitle = "8. Itens com Bordas Visíveis",
        listBackgroundColor = Color(0xFFE8EAF6), // Lilás claro
        listPadding = PaddingValues(16.dp),
        titlePadding = PaddingValues(16.dp),
        itemStyle = ListItemStyle(
            textColor = Color(0xFF303F9F), // Índigo escuro
            backgroundColor = Color.Companion.White,
            padding = PaddingValues(16.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, Color(0xFF3F51B5)) // Índigo
        ),
        dividerStyle = ListDividerStyle(showBelow = false)
    )

    val dividersOnlyAboveItems: AllListsState = AllListsState(
        items = sampleItemsMedium,
        listTitle = "9. Divisores Apenas Acima do Item",
        listPadding = PaddingValues(vertical = 8.dp),
        titlePadding = PaddingValues(16.dp),
        itemStyle = ListItemStyle(
            textColor = Color.Companion.Black,
            padding = PaddingValues(16.dp)
        ),
        dividerStyle = ListDividerStyle(
            showAbove = true,
            showBelow = false,
            color = Color.Companion.Red,
            thickness = 1.dp,
            padding = PaddingValues(horizontal = 32.dp)
        )
    )

    val listWithMinimalPadding: AllListsState = AllListsState(
        items = sampleItemsLong,
        listTitle = "10. Lista com Padding Mínimo (Conteúdo Denso)",
        listBackgroundColor = Color.Companion.White,
        listPadding = PaddingValues(vertical = 4.dp),
        titlePadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        itemStyle = ListItemStyle(
            textColor = Color.Companion.Black,
            backgroundColor = Color(0xFFFAFAFA),
            padding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(2.dp)
        ),
        dividerStyle = ListDividerStyle(
            showBelow = true,
            color = Color(0xFFE0E0E0),
            thickness = 1.dp,
            padding = PaddingValues(start = 8.dp)
        )
    )

    val vibrantBackgroundList: AllListsState = AllListsState(
        items = taskItems,
        listTitle = "11. Fundo Vibrante para Destaque",
        listBackgroundColor = Color(0xFF4A148C), // Roxo escuro e vibrante
        listPadding = PaddingValues(16.dp),
        titlePadding = PaddingValues(16.dp),
        itemStyle = ListItemStyle(
            textColor = Color.Companion.White, // Texto branco para contraste
            backgroundColor = Color(0xFF6A1B9A), // Roxo um pouco mais claro para itens
            padding = PaddingValues(16.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color(0xFF8E24AA)) // Borda sutil
        ),
        dividerStyle = ListDividerStyle(
            showBelow = true,
            color = Color(0xFF7B1FA2), // Cor do divisor
            thickness = 1.dp,
            padding = PaddingValues(horizontal = 16.dp)
        )
    )

    val circularItemsList: AllListsState = AllListsState(
        items = List(3) {
            ListItemData(
                id = "circ_$it",
                text = (it + 1).toString(),
                description = "Opção"
            )
        },
        listTitle = "12. Itens com Formato Circular (Simulado)",
        listBackgroundColor = Color(0xFFE0F7FA),
        listPadding = PaddingValues(16.dp),
        titlePadding = PaddingValues(16.dp),
        itemStyle = ListItemStyle(
            textColor = Color(0xFF006064),
            backgroundColor = Color.Companion.White,
            // Para um círculo perfeito, o padding e o tamanho do conteúdo precisariam ser gerenciados
            // O shape CircleShape será aplicado, mas o conteúdo ainda é retangular.
            padding = PaddingValues(24.dp),
            shape = CircleShape, // Forma circular
            border = BorderStroke(2.dp, Color(0xFF00ACC1))
        ),
        dividerStyle = ListDividerStyle(showBelow = false)
    )

    val emphasizedTextList: AllListsState = AllListsState(
        items = sampleItemsShort.map { it.copy(text = "IMPORTANTE: ${it.text}") },
        listTitle = "13. Texto do Item Enfatizado",
        listBackgroundColor = Color.Companion.White,
        listPadding = PaddingValues(vertical = 8.dp),
        titlePadding = PaddingValues(16.dp),
        itemStyle = ListItemStyle(
            textColor = Color.Companion.Red, // Cor de destaque para o texto
            // Você precisaria de mais campos em ListItemStyle ou ListItemData para controlar o fontWeight do texto
            // e da descrição separadamente se quisesse, por exemplo, fontWeight = FontWeight.Bold.
            // Por ora, apenas a cor será diferente.
            backgroundColor = Color(0xFFFFF0F0),
            padding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
        ),
        dividerStyle = ListDividerStyle(showBelow = true, color = Color.Companion.LightGray)
    )

    val listWithoutAnyTitle: AllListsState = AllListsState(
        items = sampleItemsMedium,
        listTitle = null, // SEM TÍTULO
        listBackgroundColor = Color(0xFFEEEEEE),
        listPadding = PaddingValues(8.dp), // Padding da lista ainda se aplica
        // titlePadding não será usado
        itemStyle = ListItemStyle(
            textColor = Color.Companion.DarkGray,
            backgroundColor = Color.Companion.White,
            padding = PaddingValues(12.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
        ),
        dividerStyle = ListDividerStyle(showBelow = true, color = Color.Companion.Gray)
    )

    fun getAllListStates(): List<AllListsState> {
        return listOf(
            basicListWithDividers,
            compactListNoDividers,
            roundedItemsList,
            darkThemeList,
            insetDividersList,
            cutCornerItemsList,
            fullWidthDividersThick,
            itemsWithVisibleBorders,
            dividersOnlyAboveItems,
            listWithMinimalPadding,
            vibrantBackgroundList,
            circularItemsList,
            emphasizedTextList,
            listWithoutAnyTitle
            // Adicione mais estados aqui
        )
    }
}