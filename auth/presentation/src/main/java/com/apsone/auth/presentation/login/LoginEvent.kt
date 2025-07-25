package com.apsone.auth.presentation.login

import com.apsone.core.presentation.ui.UiText

sealed interface LoginEvent {
    data class Error(val error: UiText): LoginEvent
    data object LoginSuccess : LoginEvent
}