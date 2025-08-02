package com.arthurabreu.allthingsandroid.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arthurabreu.allthingsandroid.R

val gothicA1 = FontFamily(
    listOf(
        Font(resId = R.font.gothica1_regular, weight = FontWeight.Normal),
        Font(resId = R.font.gothica1_medium, weight = FontWeight.Medium),
        Font(resId = R.font.gothica1_semibold, weight = FontWeight.SemiBold),
        Font(resId = R.font.gothica1_bold, weight = FontWeight.Bold),
        Font(resId = R.font.gothica1_black, weight = FontWeight.Black),
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodySmall = TextStyle(
        color = AquaBlue,
        fontFamily = gothicA1,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    headlineSmall = TextStyle(
        color = TextWhite,
        fontFamily = gothicA1,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    headlineMedium = TextStyle(
        color = TextWhite,
        fontFamily = gothicA1,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
)