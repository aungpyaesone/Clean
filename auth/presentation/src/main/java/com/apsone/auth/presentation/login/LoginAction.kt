package com.apsone.auth.presentation.login

sealed interface LoginAction {
    data object OnTogglePasswordVisibility : LoginAction
    data object OnLoginClick: LoginAction
    data object OnRegisterClick : LoginAction
}