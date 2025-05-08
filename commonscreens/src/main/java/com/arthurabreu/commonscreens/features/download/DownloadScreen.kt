package com.arthurabreu.commonscreens.features.download

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomProgressBar(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray
) {
    Box(
        modifier = modifier
            .height(24.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress / 100)
                .fillMaxHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(color)
                .animateContentSize()
        )
    }
}

@Composable
fun StartDownload(
    isStartDownload: Boolean = true,
    startDownload: () -> Unit,
    onColorPick: () -> Unit
) {
    Button(onClick = { startDownload() }) {
        if (isStartDownload) Text("Start Download")
        else Text("Retry")
    }
    Button(onClick = { onColorPick() }) {
        Text("Change Progress Color")
    }
}

@Composable
fun DownloadProgress(
    progress: Float,
    progressColor: Color
) {
    CustomProgressBar(
        progress = progress,
        color = progressColor,
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        text = "Downloading: ${progress.toInt()}%",
        modifier = Modifier.padding(vertical = 8.dp)
    )
}