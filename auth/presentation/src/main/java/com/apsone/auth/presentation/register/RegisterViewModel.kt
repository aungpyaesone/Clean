package com.apsone.auth.presentation.register
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apsone.auth.presentation.R
import com.apsone.core.domain.util.DataError
import com.apsone.core.domain.util.Result
import com.apsone.core.presentation.ui.UiText
import com.apsone.core.presentation.ui.asUiText
import com.apsone.core.presentation.ui.textAsFlow
import com.apsone.domain.AuthRepository
import com.apsone.domain.UserDataValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        state.email.textAsFlow()
            .onEach { email ->
                val isValidEmail =  userDataValidator.isValidEmail(email.toString())
                state = state.copy(
                    isEmailValid =isValidEmail,
                    canRegister = isValidEmail && state.passwordValidationState.isValid && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)

        state.password.textAsFlow()
            .onEach { password ->
                val passwordValidationState =userDataValidator.isValidPassword(password.toString())
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = state.isEmailValid && passwordValidationState.isValid && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)
    }

    fun action(action: RegisterAction) {
         when (action) {
             is RegisterAction.OnTogglePasswordVisibilityClick -> {
                 state = state.copy(
                     isPasswordVisible = !state.isPasswordVisible
                 )
             }
             RegisterAction.OnRegisterClick -> register() 
             else -> Unit
         }
    }

    private fun register() {
        viewModelScope.launch {
            state = state.copy(
                isRegistering = true
            )
            val result = authRepository.register(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state = state.copy(
                isRegistering = false
            )

            when(result){
                is Result.Error -> {
                    if(result.error == DataError.Network.CONFLICT){
                        eventChannel.send(
                            RegisterEvent.Error(
                                UiText.StringResource(
                                    R.string.error_user_already_exists
                                )
                            )
                        )
                    }else{
                        eventChannel.send(RegisterEvent.Error(result.error.asUiText()))
                    }

                }
                is Result.Success -> eventChannel.send(RegisterEvent.RegistrationSuccess)
            }

        }
    }
}