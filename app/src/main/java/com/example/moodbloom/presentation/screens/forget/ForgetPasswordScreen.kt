package com.example.moodbloom.presentation.screens.forget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodbloom.MainViewModel
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.auth.LoginRequestModel
import com.example.moodbloom.extension.ResponseStates
import com.example.moodbloom.extension.SpacerHeight
import com.example.moodbloom.extension.SpacerWeight
import com.example.moodbloom.extension.isValidEmail
import com.example.moodbloom.extension.isValidPassword
import com.example.moodbloom.presentation.components.HandleApiStates
import com.example.moodbloom.presentation.components.InputType
import com.example.moodbloom.presentation.components.PromptTypeShow
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TextButton
import com.example.moodbloom.presentation.components.TextInputField
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.components.textSdp
import com.example.moodbloom.presentation.screens.login.LoginViewModel
import com.example.moodbloom.routes.ScreensRoute
import com.example.moodbloom.ui.typo.HeadlineMediumText
import com.google.firebase.auth.FirebaseUser

@Composable
fun ForgetPasswordScreenRoute(
    onNavigate: (String) -> Unit,
    mainViewModel: MainViewModel,
    viewModel: ForgetViewModel = hiltViewModel()
) {

    val resetEmail by viewModel.resetEmail.collectAsStateWithLifecycle()
    ForgetPasswordScreen(
        onResetSuccess = {
            viewModel.clearState()
            onNavigate(ScreensRoute.Login.route)
        }, resetEmail = resetEmail,
        sendPasswordResetEmail = viewModel::sendPasswordResetEmail
    )
}

@Composable
internal fun ForgetPasswordScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(),
    resetEmail: ResponseStates<Boolean> = ResponseStates.Idle,
    sendPasswordResetEmail: (String) -> Unit = {},
    onResetSuccess: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerHeight(7.hpr)
            ResourceImage(image = R.drawable.ic_logo, modifier = Modifier.height(12.hpr))
            SpacerHeight(5.hpr)
            HeadlineMediumText(
                text = "Forget Password", fontSize = 48.textSdp
            )
            SpacerWeight(.5f)
                TextInputField(
                    value = email,
                    onValueChange = { email = it },
                    singleLine = true,
                    placeholder = "Enter your email",
                    label = "Email",
                )

            SpacerWeight(1f)

            TextButton(
                modifier = Modifier.padding(horizontal = 10.sdp), text = "Reset Password", onClick = {
                    val errorMessage: String =
                        if (!email.isValidEmail()) {
                            "You are requested to provide valid email address."
                        }else {
                            ""
                        }
                    if (errorMessage.isBlank()) {
                        sendPasswordResetEmail(email)
                    } else {
                        promptsViewModel.updatePrompt(
                            promptTypeShow = PromptTypeShow.Error(
                                message = errorMessage,
                                onButtonClick = {},
                                onDismiss = {
                                    promptsViewModel.updatePrompt(null)
                                }
                            )
                        )
                    }

                })
            SpacerWeight(1f)
        }
    }

    HandleApiStates(
        state = resetEmail,
        updatePrompt = promptsViewModel::updatePrompt
    ) { it ->
        LaunchedEffect(Unit) {
            promptsViewModel.updatePrompt(
                PromptTypeShow.Warning(
                    message = "Reset password link send to your email address",
                    onButtonClick = {},
                    onDismiss = {
                        onResetSuccess()
                        promptsViewModel.updatePrompt(null) }
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewForgetPasswordScreen() {
    ForgetPasswordScreen {}
}