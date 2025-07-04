package com.arthurabreu.commonscreens.ui.state.login

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import com.arthurabreu.commonscreens.ui.state.textfields.AllTextFieldsState

@Stable
data class AllLoginsState(
    val cpfState: AllTextFieldsState,
    val password: String,
    val onPasswordChange: (String) -> Unit,
    val onLoginClick: () -> Unit,
    val isLoading: Boolean = false,
    val appIcon: ImageVector,
    val iconContentDescription: String? = null,
    val allTextFieldsState: AllTextFieldsState
)