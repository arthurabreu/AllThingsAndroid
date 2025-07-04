package com.arthurabreu.allthingsandroid.ui.viewmodel.login

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Text
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurabreu.allthingsandroid.utils.CpfUtils
import com.arthurabreu.allthingsandroid.utils.CpfVisualTransformation
import com.arthurabreu.commonscreens.ui.state.login.AllLoginsState
import com.arthurabreu.commonscreens.ui.state.textfields.AllTextFieldsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    private val _numberFieldState = MutableStateFlow(
        AllTextFieldsState(
            value = "",
            onValueChange = ::onNumberChange,
            label = "Number Field (Digits Only)",
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = false,
            supportingText = null
        )
    )

    private val _loginState = MutableStateFlow(
        AllLoginsState(
            cpfState = AllTextFieldsState(
                value = "",
                onValueChange = ::onCpfChange,
                label = "CPF",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = CpfVisualTransformation()
            ),
            password = "",
            onPasswordChange = ::onPasswordChange,
            onLoginClick = ::onLoginClick,
            isLoading = false,
            appIcon = Icons.Rounded.AccountCircle,
            allTextFieldsState = _numberFieldState.value
        )
    )
    val loginState: StateFlow<AllLoginsState> = _loginState.asStateFlow()

    private fun onCpfChange(newCpf: String) {
        val digitsOnly = newCpf.filter { it.isDigit() }.take(11)

        _loginState.update { currentState ->
            currentState.copy(
                cpfState = currentState.cpfState.copy(
                    value = digitsOnly,
                    isError = digitsOnly.length < 11 && digitsOnly.isNotEmpty()
                )
            )
        }
    }

    private fun onPasswordChange(newPassword: String) {
        _loginState.update { it.copy(password = newPassword) }
    }

    private fun onLoginClick() {
        val cpf = _loginState.value.cpfState.value
        if (!CpfUtils.isCpfValid(CpfUtils.formatPartialCpf(cpf))) {
            _loginState.update { currentState ->
                currentState.copy(
                    cpfState = currentState.cpfState.copy(
                        isError = true,
                        supportingText = { Text("Invalid CPF format") }
                    )
                )
            }
            return
        }

        viewModelScope.launch {
            _loginState.update { it.copy(isLoading = true) }
            // Simulate network call
            delay(2000)
            _loginState.update { it.copy(isLoading = false) }
        }
    }

    fun onNumberChange(newNumber: String) {
        val isError = newNumber.isNotEmpty() && newNumber.toFloatOrNull() == null
        _numberFieldState.update { currentState ->
            currentState.copy(
                value = newNumber,
                isError = isError,
                supportingText = { if (isError) Text("Please enter only numbers") }
            )
        }
        // Also update the nested state within loginState
        _loginState.update { it.copy(allTextFieldsState = _numberFieldState.value) }
    }
}