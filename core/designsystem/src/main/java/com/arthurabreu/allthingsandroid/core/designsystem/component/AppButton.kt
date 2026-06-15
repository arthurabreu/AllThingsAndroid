package com.arthurabreu.allthingsandroid.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arthurabreu.allthingsandroid.core.designsystem.theme.AllThingsAndroidTheme

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun AppOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
        ),
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    TextButton(onClick = onClick, modifier = modifier, enabled = enabled) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}

@Preview(name = "AppButton – light", showBackground = true)
@Composable
private fun AppButtonPreview() {
    AllThingsAndroidTheme {
        AppButton(text = "Primary Action", onClick = {}, modifier = Modifier.fillMaxWidth())
    }
}

@Preview(name = "AppButton – dark", showBackground = true, uiMode = 0x20)
@Composable
private fun AppButtonDarkPreview() {
    AllThingsAndroidTheme(darkTheme = true) {
        AppButton(text = "Primary Action", onClick = {}, modifier = Modifier.fillMaxWidth())
    }
}
