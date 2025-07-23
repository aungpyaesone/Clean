package com.apsone.auth.presentation.register
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import com.apsone.domain.PasswordValidationState

data class RegisterState(
    val email: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isRegistering: Boolean = false,
    val canRegister: Boolean = passwordValidationState.isValid && !isRegistering
)
