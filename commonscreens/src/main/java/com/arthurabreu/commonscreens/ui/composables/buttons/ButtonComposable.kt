package com.arthurabreu.commonscreens.ui.composables.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arthurabreu.allthingsandroid.commonscreens.R
import com.arthurabreu.commonscreens.ui.state.buttons.AllButtonsState

@Composable
fun ButtonComposable(state: AllButtonsState) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { state.onClick },
            enabled = state.enabled && !state.loading,
            modifier = state.modifier,
            colors = state.colors,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = state.elevation)
        ) {
            ButtonContent(state = state)
        }
    }
}

@Composable
private fun ButtonContent(
    state: AllButtonsState
) {
    state.iconLeft?.let {
        Icon(
            imageVector = it,
            contentDescription = state.iconDescription,
            modifier = Modifier.size(ButtonDefaults.IconSize),
            tint = state.colors.contentColor
        )
    }
    Spacer(Modifier.width(ButtonDefaults.IconSpacing))
    Text(
        text = state.text,
        color = state.colors.contentColor
    )
    Spacer(Modifier.width(ButtonDefaults.IconSpacing))
    state.iconRight?.let {
        Icon(
            imageVector = it,
            contentDescription = state.iconDescription,
            modifier = Modifier.size(ButtonDefaults.IconSize),
            tint = state.colors.contentColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BetterButtonComposablePreview() {
    ButtonComposable(
        state = AllButtonsState(
            text = stringResource(R.string.click_here),
            iconLeft = Icons.Filled.Favorite,
            iconRight = Icons.Filled.Delete,
            iconDescription = "Favorite",
            onClick = {},
            enabled = true,
            loading = false,
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6200EE),
                contentColor = Color.White,
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.Gray
            ),
            elevation = 4.dp
        )
    )
}