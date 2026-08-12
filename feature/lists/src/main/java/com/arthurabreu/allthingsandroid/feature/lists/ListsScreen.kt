package com.arthurabreu.allthingsandroid.feature.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsScreen(viewModel: ListsViewModel, onBack: () -> Unit = {}) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("lists-screen"),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Paged lists",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                navigationIcon = { ListsBackIcon(onBack) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        bottomBar = {
            if (state.error == null) {
                ListsPaginationBar(
                    state = state,
                    onPrevious = viewModel::previousPage,
                    onNext = viewModel::nextPage,
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            ListsSearchAndLayoutSwitcher(
                query = state.query,
                layout = state.layout,
                onQuery = viewModel::onQuery,
                onLayout = viewModel::setLayout,
                onSimulateError = viewModel::fail,
            )

            when {
                state.error != null -> {
                    ListsErrorState(
                        message = state.error.orEmpty(),
                        onRetry = viewModel::retry,
                    )
                }
                state.visible.isEmpty() -> ListsEmptyState(query = state.query)
                state.layout == ListsLayout.Inbox -> {
                    ListsInboxLayout(
                        rows = state.visible,
                        contentPadding = PaddingValues(bottom = 8.dp),
                    )
                }
                else -> {
                    ListsBoardLayout(
                        rows = state.visible,
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp),
                    )
                }
            }
        }
    }
}
