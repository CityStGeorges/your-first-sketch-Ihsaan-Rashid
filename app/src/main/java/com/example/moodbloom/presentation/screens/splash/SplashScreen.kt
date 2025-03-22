package com.example.moodbloom.presentation.screens.splash

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.example.moodbloom.R
import com.example.moodbloom.utils.extension.SpacerHeight
import com.example.moodbloom.utils.extension.SpacerWeight
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.routes.ScreensRoute
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
fun RequestNotificationPermission() {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

@Composable
internal fun SplashScreen(
    splashState: SplashState = SplashState.Loading,
    promptsViewModel: PromptsViewModel = hiltViewModel(), onSplashCompleted: (String) -> Unit
) {
    RequestNotificationPermission()
    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
                ResourceImage(modifier = Modifier.padding(start = 15.sdp), image = LottieCompositionSpec.RawRes(R.raw.anim_splash))
                Column(modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.background), horizontalAlignment = Alignment.CenterHorizontally) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.primary, modifier = Modifier.height(2.sdp).
                    fillMaxWidth().padding(horizontal = 20.sdp))
                    ResourceImage(image = R.drawable.mood_bloom, modifier = Modifier.padding(30.sdp))
                    SpacerHeight(164.sdp)
                }
            }

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