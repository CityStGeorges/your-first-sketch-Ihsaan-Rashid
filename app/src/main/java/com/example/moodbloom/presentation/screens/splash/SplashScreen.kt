package com.example.moodbloom.presentation.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodbloom.R
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.routes.ScreensRoute
import com.example.moodbloom.ui.theme.AppTheme

@Composable
fun SplashRoute(
    onSplashCompleted: (String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val splashState by viewModel.splashState
    SplashScreen(splashState = splashState, onSplashCompleted = onSplashCompleted)

    LaunchedEffect(Unit) {
        viewModel.startSplash()
    }

}


@Composable
internal fun SplashScreen(
    splashState: SplashState = SplashState.Loading,
    promptsViewModel: PromptsViewModel = hiltViewModel(), onSplashCompleted: (String) -> Unit
) {

    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ResourceImage(image = R.drawable.ic_logo, modifier  = Modifier.height(150.sdp))
        }
    }

    when (splashState) {
        is SplashState.Loading -> {}
        is SplashState.Success -> {
            LaunchedEffect(Unit) {
                onSplashCompleted(ScreensRoute.Welcome.route)
            }
        }

        is SplashState.Error -> {}
    }
}


@Preview
@Composable
fun PreviewSplashScreen() {

    AppTheme {
        Surface {
            SplashScreen{}
        }
    }
}