package com.arthurabreu.allthingsandroid.feature.feed.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppEmptyState
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppErrorState
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppLoadingIndicator
import com.arthurabreu.allthingsandroid.core.designsystem.theme.AppTheme
import com.arthurabreu.allthingsandroid.feature.feed.domain.model.FeedItem
import com.arthurabreu.allthingsandroid.feature.feed.presentation.components.FeedItemCard
import com.arthurabreu.allthingsandroid.feature.feed.presentation.components.FeedLoadStateFooter
import com.arthurabreu.allthingsandroid.feature.feed.presentation.viewmodel.FeedViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(viewModel: FeedViewModel = koinViewModel()) {
    val items: LazyPagingItems<FeedItem> = viewModel.feedPagingData.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Feed", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
    ) { paddingValues ->
        when (val refresh = items.loadState.refresh) {
            is LoadState.Loading -> AppLoadingIndicator(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
            )

            is LoadState.Error -> AppErrorState(
                message = refresh.error.message ?: "Something went wrong",
                onRetry = { items.retry() },
                modifier = Modifier.fillMaxSize().padding(paddingValues),
            )

            is LoadState.NotLoading -> if (items.itemCount == 0) {
                AppEmptyState(
                    message = "No posts yet",
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(
                        horizontal = AppTheme.spacing.medium,
                        vertical = AppTheme.spacing.small,
                    ),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
                ) {
                    items(
                        count = items.itemCount,
                        key = items.itemKey { it.id },
                    ) { index ->
                        items[index]?.let { FeedItemCard(item = it) }
                    }
                    item {
                        FeedLoadStateFooter(
                            loadState = items.loadState.append,
                            onRetry = { items.retry() },
                        )
                    }
                }
            }
        }
    }
}
