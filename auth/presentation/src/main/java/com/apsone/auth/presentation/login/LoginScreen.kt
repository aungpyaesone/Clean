package com.apsone.auth.presentation.login

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsone.auth.presentation.R
import com.apsone.auth.presentation.register.ClickableAnnotatedText
import com.apsone.auth.presentation.register.RegisterAction
import com.apsone.core.presentation.designsystem.CleanGray
import com.apsone.core.presentation.designsystem.CleanTheme
import com.apsone.core.presentation.designsystem.EmailIcon
import com.apsone.core.presentation.designsystem.Poppins
import com.apsone.core.presentation.designsystem.components.CleanActionButton
import com.apsone.core.presentation.designsystem.components.CleanPasswordTextField
import com.apsone.core.presentation.designsystem.components.CleanTextField
import com.apsone.core.presentation.designsystem.components.GradientBackground
import com.apsone.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable
fun LoginScreenRoot(
    viewModel:  LoginViewModel= koinViewModel(),
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val context =  LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(viewModel.events) {
        when(it){
            is LoginEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    it.error.asString(context),
                    Toast.LENGTH_SHORT).show()
            }
            LoginEvent.LoginSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    R.string.login_successful,
                    Toast.LENGTH_SHORT).show()
                onLoginSuccess()
            }
        }
    }
    LoginScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action){
                LoginAction.OnRegisterClick -> onSignUpClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 32.dp)
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.hi_there),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = stringResource(id = R.string.welcome_text),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(48.dp))

                CleanTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = state.email,
                    startIcon = EmailIcon,
                    endIcon = null,
                    hint = stringResource(id = R.string.example_email),
                    title = stringResource(id =R.string.email),
                )
                Spacer(modifier = Modifier.height(16.dp))
                CleanPasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = state.password,
                    isPasswordVisible = state.isPasswordVisible,
                    onTogglePasswordVisibilityClick = {
                        onAction(LoginAction.OnTogglePasswordVisibility)
                    },
                    hint = stringResource(R.string.password),
                    title = stringResource(R.string.password),
                )
                Spacer(modifier = Modifier.height(32 .dp))
                CleanActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.login),
                    isLoadings = state.isLoggingIn,
                    enabled = state.canLogin && !state.isLoggingIn,
                    onClick = {
                        onAction(LoginAction.OnLoginClick)
                    }
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .navigationBarsPadding(),
            ) {
                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Poppins,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ){
                        append(stringResource(R.string.dont_have_an_account) + " ")
                        pushStringAnnotation(
                            tag = "clickable_text",
                            annotation = stringResource(com.apsone.core.presentation.ui.R.string.sign_up)
                        )
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Poppins,
                                color = MaterialTheme.colorScheme.primary
                            )
                        ){
                            append(stringResource(com.apsone.core.presentation.ui.R.string.sign_up))
                        }
                        pop()
                    }
                }
                ClickableAnnotatedText(annotatedString) { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "clickable_text",
                        start = offset,
                        end = offset + 1
                    ).firstOrNull()?.let {
                        onAction(LoginAction.OnRegisterClick)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
@Preview
@Composable
private fun LoginScreenPreview() {
    CleanTheme{
       LoginScreen(
           state = LoginState(),
           onAction = {}
       )

   }

}