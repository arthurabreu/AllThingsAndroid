package com.arthurabreu.allthingsandroid.data.meditation

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class FeatureMeditation(
    val title: String,
    @DrawableRes val iconId: Int,
    val lightColor: Color,
    val mediumColor: Color,
    val darkColor: Color
)