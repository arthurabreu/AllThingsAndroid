package com.arthurabreu.commonscreens.ui.screens.login

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.arthurabreu.allthingsandroid.commonscreens.R
import com.arthurabreu.commonscreens.ui.composables.login.LoginComposable
import com.arthurabreu.commonscreens.ui.state.login.AllLoginsState
import com.arthurabreu.commonscreens.ui.state.textfields.AllTextFieldsState

@Composable
fun LoginScreenFake() {
    var cpf by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Number Field with Error
    var number by remember { mutableStateOf("") }
    val isError = number.isNotEmpty() && number.toFloatOrNull() == null
    val numberState = AllTextFieldsState(
        value = number,
        onValueChange = { number = it },
        label = "Number Field (Digits Only)",
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = isError,
        supportingText = { if (isError) Text("Please enter only numbers") }
    )

    val loginState = AllLoginsState(
        cpfState = AllTextFieldsState(
            value = cpf,
            onValueChange = { cpf = it },
            label = stringResource(R.string.cpf),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        ),
        password = password,
        onPasswordChange = { password = it },
        onLoginClick = {
            isLoading = true
        },
        isLoading = isLoading,
        appIcon = Icons.Rounded.AccountCircle,
        iconContentDescription = stringResource(R.string.app_icon),
        allTextFieldsState = numberState
    )

    LoginComposable(state = loginState)
}

@Preview(showBackground = true)
@Composable
private fun LoginExampleScreenPreview() {
    MaterialTheme {
        LoginScreenFake()
    }
}