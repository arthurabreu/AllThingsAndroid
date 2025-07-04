package com.arthurabreu.commonscreens.ui.composables.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arthurabreu.allthingsandroid.commonscreens.R
import com.arthurabreu.commonscreens.ui.composables.textfields.AllTextFieldsComposable
import com.arthurabreu.commonscreens.ui.state.login.AllLoginsState
import com.arthurabreu.commonscreens.ui.state.textfields.AllTextFieldsState

@Composable
fun LoginComposable(state: AllLoginsState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = state.appIcon,
            contentDescription = state.iconContentDescription,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(32.dp))

        AllTextFieldsComposable(
            state = state.cpfState,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = state.onPasswordChange,
            label = { Text(stringResource(R.string.senha)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(onClick = state.onLoginClick) {
                Text(stringResource(R.string.login))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    var cpf by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LoginComposable(
        state = AllLoginsState(
            cpfState = AllTextFieldsState(
                value = cpf,
                onValueChange = { cpf = it },
                label = stringResource(R.string.cpf),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = false,
                supportingText = { Text("Please enter a valid CPF") }
            ),
            password = password,
            onPasswordChange = { password = it },
            onLoginClick = { /* Handle login */ },
            isLoading = false,
            appIcon = Icons.Rounded.AccountCircle,
            iconContentDescription = stringResource(R.string.app_icon),
            allTextFieldsState = AllTextFieldsState(
                value = "",
                onValueChange = {},
                label = "Number Field (Digits Only)",
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = false,
                supportingText = { Text("Please enter only numbers") }
            )
        )
    )
}