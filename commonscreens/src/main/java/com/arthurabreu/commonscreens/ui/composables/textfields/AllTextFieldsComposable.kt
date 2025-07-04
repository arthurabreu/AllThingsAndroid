package com.arthurabreu.commonscreens.ui.composables.textfields

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.arthurabreu.allthingsandroid.commonscreens.R
import com.arthurabreu.commonscreens.ui.state.textfields.AllTextFieldsState

@Composable
fun AllTextFieldsComposable(
    state: AllTextFieldsState,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = state.value,
        onValueChange = state.onValueChange,
        label = { Text(state.label.toString()) },
        singleLine = state.singleLine,
        maxLines = state.maxLines,
        isError = state.isError,
        supportingText = state.supportingText,
        visualTransformation = state.visualTransformation,
        keyboardOptions = state.keyboardOptions,
        keyboardActions = state.keyboardActions,
        leadingIcon = state.leadingIcon,
        trailingIcon = state.trailingIcon,
        shape = state.shape(),
        colors = state.colors(),
        enabled = state.enabled,
        readOnly = state.readOnly,
        textStyle = state.textStyle,
        placeholder = state.placeholder,
        prefix = state.prefix,
        suffix = state.suffix,
        minLines = state.minLines,
        interactionSource = state.interactionSource ?: remember { MutableInteractionSource() }
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewAllTextFieldsComposable() {
    AllTextFieldsComposable(
        state = AllTextFieldsState(
            value = stringResource(R.string.just_text),
            onValueChange = {},
            label = stringResource(R.string.label),
            singleLine = true,
            maxLines = 1,
            isError = false,
            supportingText = { Text("Supporting Text") },
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions(),
            leadingIcon = null,
            trailingIcon = null
        )
    )
}