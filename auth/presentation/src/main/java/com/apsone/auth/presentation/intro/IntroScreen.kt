package com.apsone.auth.presentation.intro

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsone.core.presentation.designsystem.CleanTheme
import com.apsone.core.presentation.designsystem.LogoIcon
import com.apsone.core.presentation.designsystem.components.CleanActionButton
import com.apsone.core.presentation.designsystem.components.CleanOutlineActionButton
import com.apsone.core.presentation.designsystem.components.GradientBackground
import com.apsone.core.presentation.ui.R

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable
fun IntroScreenRoot(
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
){
    IntroScreen(
        onAction = { action ->
            when (action) {
                IntroAction.OnSignInClick -> onSignInClick()
                IntroAction.OnSignUpClick -> onSignUpClick()
            }
        } )

}

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable
fun IntroScreen(
    onAction: (IntroAction) -> Unit,
){
    GradientBackground {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ){
            CleanLogoVertical()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 16.dp)
        ){
            Text(text = stringResource(R.string.welcome_to_runique),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp)
            Spacer(Modifier.height(8.dp))
            Text(text = stringResource(R.string.runique_descritption),
                style = MaterialTheme.typography.bodySmall,
                )
            Spacer(Modifier.height(32.dp))
            CleanOutlineActionButton(
                text = stringResource(id = R.string.sign_in),
                isLoadings = false,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(IntroAction.OnSignInClick)
                }
            )
            Spacer(Modifier.height(16.dp))
            CleanActionButton(
                text = stringResource(id = R.string.sign_up),
                isLoadings = false,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(IntroAction.OnSignUpClick)
                }
            )

        }
    }
}

@Composable
private fun CleanLogoVertical(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Icon(
            imageVector = LogoIcon,
            contentDescription = "logo",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.clean),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Preview
@Composable
private fun IntroScreenPreview() {
    CleanTheme {
        IntroScreen(
            onAction = {}
        )
    }
}