package com.arthurabreu.allthingsandroid.feature.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.collectAsState
import com.arthurabreu.allthingsandroid.core.domain.PortfolioCatalog
import com.arthurabreu.allthingsandroid.core.model.CatalogSection
import com.arthurabreu.allthingsandroid.core.ui.FeatureCard
import com.arthurabreu.allthingsandroid.core.ui.SectionTitle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CatalogState(val sections: List<CatalogSection> = PortfolioCatalog.sections())

class CatalogViewModel : ViewModel() {
    private val _state = MutableStateFlow(CatalogState())
    val state: StateFlow<CatalogState> = _state.asStateFlow()

    fun routeFor(id: String): String? =
        _state.value.sections.flatMap { it.items }.find { it.id == id }?.route
}

@Composable
fun CatalogScreen(viewModel: CatalogViewModel, onOpen: (String) -> Unit) {
    val state by viewModel.state.collectAsState()
    LazyColumn(Modifier.padding(16.dp).testTag("catalog-list")) {
        state.sections.forEach { section ->
            item { SectionTitle(section.title) }
            items(section.items, key = { it.id }) { item ->
                FeatureCard(item) { onOpen(item.route) }
            }
        }
    }
}
