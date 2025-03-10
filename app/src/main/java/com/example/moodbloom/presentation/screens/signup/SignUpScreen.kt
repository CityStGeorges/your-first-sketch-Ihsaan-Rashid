package com.example.moodbloom.presentation.screens.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodbloom.MainViewModel
import com.example.moodbloom.R
import com.example.moodbloom.extension.SpacerHeight
import com.example.moodbloom.extension.SpacerWeight
import com.example.moodbloom.presentation.components.InputType
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TextButton
import com.example.moodbloom.presentation.components.TextInputField
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.components.textSdp
import com.example.moodbloom.ui.typo.HeadlineMediumText

@Composable
fun SignUpScreenRoute(
    onNavigate: (String) -> Unit,
    mainViewModel: MainViewModel,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    SignUpScreen(onNavigate=onNavigate)



}

@Composable
internal fun SignUpScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(), onNavigate: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            SpacerHeight(7.hpr)
            ResourceImage(image = R.drawable.ic_logo, modifier = Modifier.height(12.hpr))
            SpacerHeight(5.hpr)
            HeadlineMediumText(text = "Sign Up", fontSize = 48.textSdp)
            SpacerWeight(.5f)
            TextInputField(
                value = name,
                onValueChange = { name = it },
                placeholder = "Enter your full name",
                label = "Name",
                inputType = InputType.IsLetter
            )
            SpacerHeight(2.hpr)
            TextInputField(
                value = userName,
                onValueChange = { userName = it },
                placeholder = "Enter your username",
                label = "Username",
                inputType = InputType.IsLetterOrDigit
            )
            SpacerHeight(2.hpr)
            TextInputField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Enter your email",
                label = "Email",
                inputType = InputType.IsLetterOrDigit
            )
            SpacerHeight(2.hpr)
            TextInputField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Enter your password",
                label = "Password",
                inputType = InputType.IsLetterOrDigit
            )
            SpacerWeight(1f)
            TextButton(
                Modifier.padding(horizontal = 10.sdp), text = "REGISTER", onClick = {

                })
            SpacerWeight(1f)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen{}
}