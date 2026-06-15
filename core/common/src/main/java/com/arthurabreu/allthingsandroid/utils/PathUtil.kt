package com.arthurabreu.allthingsandroid.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

fun Path.standardQuadFromTo(from: Offset, to: Offset) {
    quadraticTo(
        from.x,
        from.y,
        (from.x + to.x) / 2f,
        (from.y + to.y) / 2f
    )
}