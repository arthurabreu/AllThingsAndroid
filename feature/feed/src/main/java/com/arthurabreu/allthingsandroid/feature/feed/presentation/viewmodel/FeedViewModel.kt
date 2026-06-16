package com.arthurabreu.allthingsandroid.feature.feed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arthurabreu.allthingsandroid.feature.feed.domain.model.FeedItem
import com.arthurabreu.allthingsandroid.feature.feed.domain.usecase.GetFeedUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FeedViewModel(getFeed: GetFeedUseCase) : ViewModel() {

    val feedPagingData: StateFlow<PagingData<FeedItem>> =
        getFeed()
            .cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PagingData.empty(),
            )
}
