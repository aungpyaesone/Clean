package com.apsone.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apsone.auth.presentation.R
import com.apsone.core.domain.util.DataError
import com.apsone.core.domain.util.Result
import com.apsone.core.presentation.ui.UiText
import com.apsone.core.presentation.ui.textAsFlow
import com.apsone.domain.AuthRepository
import com.apsone.domain.UserDataValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.apsone.core.presentation.ui.asUiText

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
): ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        combine(
            state.email.textAsFlow(),
            state.password.textAsFlow()
        ) {
            email, password ->
            state = state.copy(
                canLogin = userDataValidator.isValidEmail(email.toString().trim()) &&
                        password.isNotEmpty()
            )
        }.launchIn(viewModelScope)
    }
    fun onAction(action: LoginAction){
        when(action){
            LoginAction.OnLoginClick -> login()
            LoginAction.OnRegisterClick -> {

            }
            LoginAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)
            val result = authRepository.login(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state = state.copy(isLoggingIn = false)
            when(result){
                is Result.Error -> {
                    if(result.error == DataError.Network.UNAUTHORIZED){
                        eventChannel.send(LoginEvent.Error(UiText.StringResource(R.string.error_email_password_incorrect)))
                    }else{
                        eventChannel.send(LoginEvent.Error(result.error.asUiText()))
                    }
                }
                is Result.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }
}