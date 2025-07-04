package com.arthurabreu.commonscreens.ui.previewdata.textfields

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.arthurabreu.commonscreens.ui.state.textfields.AllTextFieldsState

object TextFieldStatesProvider {

    @Composable
    fun getAllTextFieldStates(): List<AllTextFieldsState> {
        // Basic Text Field
        var simpleText by remember { mutableStateOf("") }
        val simpleState = AllTextFieldsState(
            value = simpleText,
            onValueChange = { simpleText = it },
            label = "Simple Text Field"
        )

        // Password Field
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        val passwordState = AllTextFieldsState(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.ThumbUp else Icons.Filled.Close
                val description = if (passwordVisible) "Hide password" else "Show password"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            }
        )

        // Email Field with Icon
        var email by remember { mutableStateOf("") }
        val emailState = AllTextFieldsState(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email Icon") }
        )

        // Number Field with Error
        var number by remember { mutableStateOf("") }
        val isError = number.isNotEmpty() && number.toFloatOrNull() == null
        val numberState = AllTextFieldsState(
            value = number,
            onValueChange = { number = it },
            label = "Number Field (Digits Only)",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isError,
            supportingText = { if (isError) Text("Please enter only numbers") }
        )

        // Multi-line Text Field
        var multiLineText by remember { mutableStateOf("") }
        val multiLineState = AllTextFieldsState(
            value = multiLineText,
            onValueChange = { multiLineText = it },
            label = "Multi-line Description",
            singleLine = false,
            maxLines = 5
        )

        // Custom Shape (Cut Corners)
        var cutCornerText by remember { mutableStateOf("") }
        val cutCornerState = AllTextFieldsState(
            value = cutCornerText,
            onValueChange = { cutCornerText = it },
            label = "Cut Corner Shape",
            shape = { CutCornerShape(topStart = 16.dp, bottomEnd = 16.dp) }
        )

        // Custom Colors
        var customColorText by remember { mutableStateOf("") }
        val customColorState = AllTextFieldsState(
            value = customColorText,
            onValueChange = { customColorText = it },
            label = "Custom Colors",
            colors = {
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.secondary
                )
            }
        )

        // Fully Rounded Shape
        var roundedText by remember { mutableStateOf("") }
        val roundedState = AllTextFieldsState(
            value = roundedText,
            onValueChange = { roundedText = it },
            label = "Search",
            shape = { RoundedCornerShape(50) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
        )

        // Phone Number Field
        var phoneText by remember { mutableStateOf("") }
        val phoneState = AllTextFieldsState(
            value = phoneText,
            onValueChange = { phoneText = it },
            label = "Phone Number",
            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = "Phone Icon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        // Validated Field
        var validatedText by remember { mutableStateOf("") }
        val isValid = validatedText.length > 5
        val validatedState = AllTextFieldsState(
            value = validatedText,
            onValueChange = { validatedText = it },
            label = "Validated Field (min 6 chars)",
            trailingIcon = {
                if (isValid) Icon(
                    Icons.Default.Check,
                    contentDescription = "Valid",
                    tint = Color.Green
                )
            },
            supportingText = {
                if (validatedText.isNotEmpty() && !isValid) Text("Too short!")
            },
            isError = validatedText.isNotEmpty() && !isValid
        )

        // Read-only Text Field
        val readOnlyState = AllTextFieldsState(
            value = "This is read-only text",
            onValueChange = {},
            label = "Read-Only Field",
            readOnly = true
        )

        // Disabled Text Field
        var disabledText by remember { mutableStateOf("") }
        val disabledState = AllTextFieldsState(
            value = disabledText,
            onValueChange = { disabledText = it },
            label = "Disabled Field",
            enabled = false
        )

        // Text Field with Placeholder
        var placeholderText by remember { mutableStateOf("") }
        val placeholderState = AllTextFieldsState(
            value = placeholderText,
            onValueChange = { placeholderText = it },
            label = "Field with Placeholder",
            placeholder = { Text("Enter your name") }
        )

        // Text Field with Prefix and Suffix
        var prefixSuffixText by remember { mutableStateOf("") }
        val prefixSuffixState = AllTextFieldsState(
            value = prefixSuffixText,
            onValueChange = { prefixSuffixText = it },
            label = "Field with Prefix and Suffix",
            prefix = { Text("$") },
            suffix = { Text("USD") }
        )

        // Text Field with Min Lines
        var minLinesText by remember { mutableStateOf("") }
        val minLinesState = AllTextFieldsState(
            value = minLinesText,
            onValueChange = { minLinesText = it },
            label = "Field with Min Lines",
            minLines = 3,
            singleLine = false
        )

        // Text Field with Interaction Source
        val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
        var interactionText by remember { mutableStateOf("") }
        val interactionState = AllTextFieldsState(
            value = interactionText,
            onValueChange = { interactionText = it },
            label = "Field with Interaction Source",
            interactionSource = interactionSource
        )

        // Text Field with custom text style
        var customTextStyleText by remember { mutableStateOf("") }
        val customTextStyleState = AllTextFieldsState(
            value = customTextStyleText,
            onValueChange = { customTextStyleText = it },
            label = "Custom Text Style",
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        )

        // Text Field with a character counter
        var characterCounterText by remember { mutableStateOf("") }
        val characterCounterState = AllTextFieldsState(
            value = characterCounterText,
            onValueChange = { characterCounterText = it },
            label = "Character Counter",
            supportingText = {
                Text("${characterCounterText.length} / 50 characters")
            },
            maxLines = 1,
            singleLine = true,
        )

        // Text Field with a custom leading icon
        val customLeadingIconState = AllTextFieldsState(
            value = characterCounterText,
            onValueChange = { characterCounterText = it },
            label = "Custom Leading Icon",
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") }
        )

        // Text Field with a custom trailing icon
        val customTrailingIconState = AllTextFieldsState(
            value = characterCounterText,
            onValueChange = { characterCounterText = it },
            label = "Custom Trailing Icon",
            trailingIcon = { Icon(Icons.Default.Check, contentDescription = "Check Icon") }
        )

        // Text Field with a custom keyboard options
        val customKeyboardOptionsState = AllTextFieldsState(
            value = characterCounterText,
            onValueChange = { characterCounterText = it },
            label = "Custom Keyboard Options",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        // Text Field with a custom keyboard actions
        val customKeyboardActionsState = AllTextFieldsState(
            value = characterCounterText,
            onValueChange = { characterCounterText = it },
            label = "Custom Keyboard Actions",
            keyboardActions = KeyboardActions (onDone = { /* Handle done action */ })
        )

        // Text Field with a custom visual transformation
        val customVisualTransformationState = AllTextFieldsState(
            value = characterCounterText,
            onValueChange = { characterCounterText = it },
            label = "Custom Visual Transformation",
            visualTransformation = VisualTransformation.None // No transformation for simplicity
        )

        // Text Field with a custom supporting text
        val customSupportingTextState = AllTextFieldsState(
            value = characterCounterText,
            onValueChange = { characterCounterText = it },
            label = "Custom Supporting Text",
            supportingText = { Text("This is a custom supporting text") }
        )

        // Text Field with a custom error message
        val customErrorMessageState = AllTextFieldsState(
            value = characterCounterText,
            onValueChange = { characterCounterText = it },
            label = "Custom Error Message",
            isError = characterCounterText.isEmpty(),
            supportingText = {
                if (characterCounterText.isEmpty()) {
                    Text("This field cannot be empty", color = MaterialTheme.colorScheme.error)
                }
            }
        )

        // Text Field with a custom max lines
        val customMaxLinesState = AllTextFieldsState(
            value = characterCounterText,
            onValueChange = { characterCounterText = it },
            label = "Custom Max Lines",
            maxLines = 3,
            singleLine = false
        )

        return listOf(
            simpleState,
            passwordState,
            emailState,
            numberState,
            multiLineState,
            cutCornerState,
            customColorState,
            roundedState,
            phoneState,
            validatedState,
            readOnlyState,
            disabledState,
            placeholderState,
            prefixSuffixState,
            minLinesState,
            interactionState,
            customTextStyleState,
            characterCounterState,
            customLeadingIconState,
            customKeyboardActionsState,
            customKeyboardOptionsState,
            customVisualTransformationState,
            customSupportingTextState,
            customErrorMessageState,
            customMaxLinesState
        )
    }
}