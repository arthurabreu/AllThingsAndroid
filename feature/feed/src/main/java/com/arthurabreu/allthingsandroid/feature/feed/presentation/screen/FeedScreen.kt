package com.arthurabreu.allthingsandroid.feature.feed.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.arthurabreu.allthingsandroid.feature.feed.domain.model.FeedItem
import com.arthurabreu.allthingsandroid.feature.feed.presentation.components.FeedItemCard
import com.arthurabreu.allthingsandroid.feature.feed.presentation.components.FeedLoadStateFooter
import com.arthurabreu.allthingsandroid.feature.feed.presentation.viewmodel.FeedViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(viewModel: FeedViewModel = koinViewModel()) {
    val items: LazyPagingItems<FeedItem> = viewModel.feedPagingData.collectAsLazyPagingItems()

    Scaffold(topBar = { TopAppBar(title = { Text("Feed") }) }) { paddingValues ->
        when (val refresh = items.loadState.refresh) {
            is LoadState.Loading -> Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) { CircularProgressIndicator() }

            is LoadState.Error -> Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) { Text("Error: ${refresh.error.message}") }

            is LoadState.NotLoading -> LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
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
