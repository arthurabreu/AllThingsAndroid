package com.arthurabreu.allthingsandroid.feature.player.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.arthurabreu.allthingsandroid.feature.player.data.VideoRepository
import com.arthurabreu.allthingsandroid.feature.player.domain.model.VideoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PlayerUiState(
    val catalog: List<VideoItem> = VideoRepository.getCatalog(),
    val currentItem: VideoItem? = null,
    val isPlaying: Boolean = false,
    val isInPip: Boolean = false,
)

class PlayerViewModel(app: Application) : AndroidViewModel(app) {

    val player: ExoPlayer = ExoPlayer.Builder(app).build()

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    fun play(item: VideoItem) {
        _uiState.update { it.copy(currentItem = item, isPlaying = true) }
        player.setMediaItem(MediaItem.fromUri(item.uri))
        player.prepare()
        player.play()
    }

    fun togglePlayPause() {
        if (player.isPlaying) player.pause() else player.play()
        _uiState.update { it.copy(isPlaying = player.isPlaying) }
    }

    fun seekForward() = player.seekForward()
    fun seekBack() = player.seekBack()

    fun onPipChanged(inPip: Boolean) {
        _uiState.update { it.copy(isInPip = inPip) }
    }

    override fun onCleared() {
        player.release()
        super.onCleared()
    }
}
