@file:OptIn(ExperimentalFoundationApi::class)
package com.apsone.auth.presentation.register

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsone.auth.presentation.R
import com.apsone.core.presentation.designsystem.CheckIcon
import com.apsone.core.presentation.designsystem.CleanDarkRed
import com.apsone.core.presentation.designsystem.CleanGray
import com.apsone.core.presentation.designsystem.CleanGreen
import com.apsone.core.presentation.designsystem.CleanTheme
import com.apsone.core.presentation.designsystem.CrossIcon
import com.apsone.core.presentation.designsystem.EmailIcon
import com.apsone.core.presentation.designsystem.Poppins
import com.apsone.core.presentation.designsystem.components.CleanActionButton
import com.apsone.core.presentation.designsystem.components.CleanPasswordTextField
import com.apsone.core.presentation.designsystem.components.CleanTextField
import com.apsone.core.presentation.designsystem.components.GradientBackground
import com.apsone.domain.PasswordValidationState
import com.apsone.domain.UserDataValidator
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable
fun RegisterScreenRoot(
    modifier: Modifier = Modifier,
    registerViewModel: RegisterViewModel = koinViewModel(),
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit

    ) {
    RegisterScreen(
        state = registerViewModel.state,
        onAction = registerViewModel::action
    )
}

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable
private fun RegisterScreen(
    modifier: Modifier = Modifier,
    state: RegisterState,
    onAction: (RegisterAction) -> Unit) {
    GradientBackground {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(text = stringResource(R.string.create_account),
                style = MaterialTheme.typography.headlineMedium)

            val annotatedString = buildAnnotatedString {
               withStyle(
                   style = SpanStyle(
                       fontFamily = Poppins,
                       color = CleanGray
                   )
               ){
                   append(stringResource(R.string.already_have_an_account) + " ")
                   pushStringAnnotation(
                       tag = "clickable_test",
                       annotation = stringResource(R.string.login)
                   )
                   withStyle(
                       style = SpanStyle(
                           fontWeight = FontWeight.SemiBold,
                           fontFamily = Poppins,
                           color = MaterialTheme.colorScheme.primary
                       )
                   ){
                    append(stringResource(R.string.login))
                   }
                   pop()
               }
            }
            ClickableAnnotatedText(
                annotatedString
            ) {
                annotatedString.getStringAnnotations(
                    tag = "clickable_text",
                    start = it,
                    end = it
                ).firstOrNull()?.let {
                    onAction(RegisterAction.OnLoginClick)
                }
            }
            Spacer(Modifier.height(48.dp))
            CleanTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = if(state.isEmailValid) CheckIcon else null,
                modifier = Modifier.fillMaxWidth(),
                hint = stringResource(R.string.example_email),
                title = stringResource(R.string.email),
                additionalInfo = stringResource(R.string.must_be_valid),
                keyboardType = KeyboardType.Email

            )

            Spacer(modifier = Modifier.height(16.dp))
            CleanPasswordTextField(
                state = state.password,
                modifier = Modifier.fillMaxWidth(),
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibilityClick = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                },
                hint = stringResource(R.string.password),
                title = stringResource(R.string.password)
            )

            Spacer(modifier = Modifier.height(16.dp))
            PasswordMetric(
                text = stringResource(R.string.at_least_x_characters,UserDataValidator.MIN_PASSWORD_LENGTH),
                valid = state.passwordValidationState.hasMinLength,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(4.dp))
            PasswordMetric(
                text = stringResource(R.string.at_least_one_number),
                valid = state.passwordValidationState.hasNumber,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(4.dp))
            PasswordMetric(
                text = stringResource(R.string.contains_lowercase_character),
                valid = state.passwordValidationState.hasLowerCased,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(4.dp))
            PasswordMetric(
                text = stringResource(R.string.contains_uppercase_character),
                valid = state.passwordValidationState.hasUpperCased,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(32.dp))

            CleanActionButton(
                text = stringResource(R.string.register),
                modifier = Modifier.fillMaxWidth(),
                isLoadings = state.isRegistering,
                enabled = state.canRegister,
                onClick = {
                    onAction(RegisterAction.OnRegisterClick)
                }
            )

        }
    }
}

/*ClickableText(
                text = annotatedString,
            ) {
                annotatedString.getStringAnnotations(
                    tag = "clickable_text",
                    start = it,
                    end = it
                ).firstOrNull()?.let {
                    onAction(RegisterAction.OnLoginClick)
                }
            }*/

@Composable
fun ClickableAnnotatedText(
    annotatedString: AnnotatedString,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    var layoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    BasicText(
        text = annotatedString,
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    layoutResult?.let { layout ->
                        val position = layout.getOffsetForPosition(offset)
                        onClick(position)
                    }
                }
            },
        onTextLayout = { layoutResult = it }
    )
}

@Composable
fun PasswordMetric(
    text: String,
    valid: Boolean,
    modifier: Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = if(valid){
                CheckIcon
            }else{
                CrossIcon
            },
            contentDescription = null,
            tint = if(valid){
                CleanGreen
            }else{
                CleanDarkRed
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )

    }

}


@RequiresApi(Build.VERSION_CODES.CUPCAKE)
@Preview
@Composable
fun RegisterScreenPreview(modifier: Modifier = Modifier) {
    CleanTheme {
        RegisterScreen(
            state = RegisterState(
                passwordValidationState = PasswordValidationState(
                    hasMinLength = true,
                    hasNumber = true,
                    hasLowerCased = true,
                    hasUpperCased = true
                )
            ),
            onAction = {}
        )
    }
}