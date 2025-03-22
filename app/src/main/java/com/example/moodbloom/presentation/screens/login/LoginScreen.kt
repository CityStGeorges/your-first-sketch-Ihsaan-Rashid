package com.example.moodbloom.presentation.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodbloom.presentation.MainViewModel
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.auth.LoginRequestModel
import com.example.moodbloom.domain.models.auth.UserModel
import com.example.moodbloom.utils.extension.ResponseStates
import com.example.moodbloom.utils.extension.SpacerHeight
import com.example.moodbloom.utils.extension.SpacerWeight
import com.example.moodbloom.utils.extension.isValidEmail
import com.example.moodbloom.utils.extension.isValidPassword
import com.example.moodbloom.presentation.components.HandleApiStates
import com.example.moodbloom.presentation.components.PromptTypeShow
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TextButton
import com.example.moodbloom.presentation.components.TextInputField
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.safeClickable
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.components.textSdp
import com.example.moodbloom.presentation.routes.ScreensRoute
import com.example.moodbloom.ui.typo.HeadlineMediumText
import com.google.firebase.auth.FirebaseUser

@Composable
fun LoginScreenRoute(
    onNavigate: (String) -> Unit,
    mainViewModel: MainViewModel,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val userState by viewModel.userState.collectAsStateWithLifecycle()
    LoginScreen(
        onLoginSuccess = {
            mainViewModel.firebaseUser = it.firebaseUser
            mainViewModel.userModel = it
            viewModel.clearState()
            onNavigate(ScreensRoute.Home.route)
        }, userState = userState,
        onLoginRequest = viewModel::loginUser
    )
}

@Composable
internal fun LoginScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(),
    userState: ResponseStates<UserModel?> = ResponseStates.Idle,
    onLoginRequest: (LoginRequestModel) -> Unit = {},
    onLoginSuccess: (UserModel) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }
    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerHeight(7.hpr)
            ResourceImage(image = R.drawable.ic_logo, modifier = Modifier.height(12.hpr))
            SpacerHeight(5.hpr)
            HeadlineMediumText(text = "Sign In", fontSize = 48.textSdp)
            SpacerWeight(.5f)
            TextInputField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                placeholder = "Enter your email",
                label = "Email",
            )
            SpacerHeight(2.hpr)
            TextInputField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                placeholder = "Enter your password",
                label = "Password",
                trailing = {
                    val image = if (passwordVisible) R.drawable.ic_eye_open else  R.drawable.ic_eye_close
                    ResourceImage(image = image, modifier = Modifier.size(18.sdp).safeClickable {
                        passwordVisible=!passwordVisible
                    })
                },
            )
            SpacerWeight(1f)
            TextButton(
                enabled = password.isNotBlank() && email.isNotBlank(),
                modifier = Modifier.padding(horizontal = 10.sdp), text = "LOGIN", onClick = {

                    val errorMessage: String =
                        if (!email.isValidEmail()) {
                            "You are requested to provide valid email address."
                        } else if (!password.isValidPassword()) {
                            "You are requested to enter strong password."
                        } else {
                            ""
                        }
                    if (errorMessage.isBlank()) {
                        onLoginRequest(LoginRequestModel(email = email, password = password))
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
        state = userState,
        updatePrompt = promptsViewModel::updatePrompt
    ) { it ->
        LaunchedEffect(Unit) {
            if (it != null) {
                onLoginSuccess(it)
            } else {
                promptsViewModel.updatePrompt(
                    PromptTypeShow.Error(
                        message = context.getString(R.string.we_are_unable_to_process_your_request),
                        onButtonClick = {},
                        onDismiss = { promptsViewModel.updatePrompt(null) }
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen {}
}

