package com.arthurabreu.allthingsandroid.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.arthurabreu.allthingsandroid.core.designsystem.theme.AllThingsAndroidTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    supportingText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = modifier,
        isError = isError,
        supportingText = supportingText?.let { { Text(text = it) } },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        shape = MaterialTheme.shapes.medium,
        singleLine = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    AllThingsAndroidTheme {
        AppTextField(
            value = "Hello",
            onValueChange = {},
            label = "Email",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
