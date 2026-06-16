package com.arthurabreu.allthingsandroid.feature.player.presentation.screen

import android.app.Activity
import android.app.PictureInPictureParams
import android.os.Build
import android.util.Rational
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PictureInPicture
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.ui.compose.PlayerSurface
import androidx.media3.ui.compose.SURFACE_TYPE_SURFACE_VIEW
import com.arthurabreu.allthingsandroid.feature.player.presentation.viewmodel.PlayerViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(viewModel: PlayerViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    DisposableEffect(Unit) {
        onDispose { if (viewModel.player.isPlaying) viewModel.player.pause() }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Media3 Player") }) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Video surface
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
            ) {
                PlayerSurface(
                    player = viewModel.player,
                    surfaceType = SURFACE_TYPE_SURFACE_VIEW,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            // Controls
            if (uiState.currentItem != null) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Spacer(Modifier.height(8.dp))
                    Text(uiState.currentItem!!.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(onClick = viewModel::seekBack) {
                            Icon(Icons.Default.FastRewind, contentDescription = "Seek back")
                        }
                        IconButton(onClick = viewModel::togglePlayPause) {
                            Icon(
                                if (uiState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Play/Pause",
                            )
                        }
                        IconButton(onClick = viewModel::seekForward) {
                            Icon(Icons.Default.FastForward, contentDescription = "Seek forward")
                        }
                        IconButton(onClick = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                val params = PictureInPictureParams.Builder()
                                    .setAspectRatio(Rational(16, 9))
                                    .build()
                                (context as? Activity)?.enterPictureInPictureMode(params)
                            }
                        }) {
                            Icon(Icons.Default.PictureInPicture, contentDescription = "PiP")
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Video catalog
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(uiState.catalog, key = { it.id }) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.play(item) },
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(item.title, style = MaterialTheme.typography.titleSmall)
                            Spacer(Modifier.height(4.dp))
                            Text(
                                item.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
        }
    }
}
