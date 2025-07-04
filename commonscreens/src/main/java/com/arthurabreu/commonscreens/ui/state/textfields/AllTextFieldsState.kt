package com.arthurabreu.commonscreens.ui.state.textfields

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation

@Stable
data class AllTextFieldsState(
    val value: String,
    val onValueChange: (String) -> Unit,
    val enabled: Boolean = true,
    val readOnly: Boolean = false,
    val textStyle: TextStyle = TextStyle.Default,
    val label: String? = null,
    val placeholder: (@Composable (() -> Unit))? = null,
    val leadingIcon: (@Composable (() -> Unit))? = null,
    val trailingIcon: (@Composable (() -> Unit))? = null,
    val prefix: (@Composable (() -> Unit))? = null,
    val suffix: (@Composable (() -> Unit))? = null,
    val supportingText: (@Composable (() -> Unit))? = null,
    val isError: Boolean = false,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
    val singleLine: Boolean = false,
    val maxLines: Int = Int.MAX_VALUE,
    val minLines: Int = 1,
    val interactionSource: androidx.compose.foundation.interaction.MutableInteractionSource? = null,
    val shape: @Composable () -> Shape = { OutlinedTextFieldDefaults.shape },
    val colors: @Composable () -> TextFieldColors = { OutlinedTextFieldDefaults.colors() },
)