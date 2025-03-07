package com.example.moodbloom.presentation.screens.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodbloom.R
import com.example.moodbloom.extension.SpacerHeight
import com.example.moodbloom.extension.SpacerWeight
import com.example.moodbloom.presentation.components.LoginWithGoogleButton
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TextButton
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.components.textSdp
import com.example.moodbloom.ui.typo.DisplayLargeText
import com.example.moodbloom.ui.typo.HeadlineMediumText
import com.example.moodbloom.ui.typo.LabelMediumText
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreenRoute(
    onNavigate: (String) -> Unit,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    WelcomeScreen(onNavigate = onNavigate)
}

@Composable
internal fun WelcomeScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(), onNavigate: (String) -> Unit
) {

    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    ScreenContainer(currentPrompt = currentPrompt, horizontalPadding = 50.sdp) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerHeight(7.hpr)
            ResourceImage(image = R.drawable.ic_logo, modifier = Modifier.height(12.hpr))
            SpacerHeight(5.hpr)
            HeadlineMediumText(text = "Welcome", fontSize = 48.textSdp)
            SpacerWeight(.5f)
            TextButton(
                shape = MaterialTheme.shapes.extraLarge,
                text = "Sign Up",
                onClick = {

                })
            SpacerHeight(2.hpr)
            TextButton(
                shape = MaterialTheme.shapes.extraLarge, text = "Sign In", onClick = {

                })
            SpacerHeight(2.hpr)
            LoginWithGoogleButton(
                text = "Sign in with Google", onClick = {

                })
            SpacerHeight(2.hpr)
            LabelMediumText(text = "Forgot Your Password?")
            SpacerWeight(1f)
        }
    }
}