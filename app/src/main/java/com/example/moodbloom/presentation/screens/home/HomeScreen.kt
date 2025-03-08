package com.example.moodbloom.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodbloom.R
import com.example.moodbloom.extension.SpacerHeight
import com.example.moodbloom.extension.SpacerWeight
import com.example.moodbloom.extension.SpacerWidth
import com.example.moodbloom.presentation.components.LoginWithGoogleButton
import com.example.moodbloom.presentation.components.LogoutButton
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TextButton
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.components.textSdp
import com.example.moodbloom.routes.ScreensRoute
import com.example.moodbloom.ui.typo.HeadlineMediumText
import com.example.moodbloom.ui.typo.LabelMediumText
import com.example.moodbloom.ui.typo.TitleLargeText


@Composable
fun HomeScreenRoute(
    onNavigate: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen(onNavigate = onNavigate)
}

@Composable
internal fun HomeScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(), onNavigate: (String) -> Unit
) {

    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerHeight(3.hpr)
            ResourceImage(image = R.drawable.ic_logo, modifier = Modifier.height(12.hpr))
            SpacerHeight(5.hpr)
            HeadlineMediumText(text = "Welcome Ihsaan!", fontSize = 48.textSdp)
            SpacerHeight(3.hpr)
            Row(verticalAlignment = Alignment.CenterVertically) {
                HeadlineMediumText(text = "Your Mood this week:")
                SpacerWidth(5.sdp)
                ResourceImage(image = R.drawable.ic_smile_emoji, modifier = Modifier.size(50.sdp))
            }

            SpacerHeight(5.hpr)
            TextButton(
                modifier = Modifier.padding(horizontal = 50.sdp),
                shape = MaterialTheme.shapes.extraLarge,
                text = "Log Daily Mood",
                onClick = {
                    onNavigate(ScreensRoute.SignUp.route)
                })
            SpacerHeight(2.hpr)
            TextButton(
                modifier = Modifier.padding(horizontal = 50.sdp),
                shape = MaterialTheme.shapes.extraLarge, text = "Habit Tracker", onClick = {
                    onNavigate(ScreensRoute.Login.route)
                })
            SpacerHeight(2.hpr)
            TextButton(
                modifier = Modifier.padding(horizontal = 50.sdp),
                shape = MaterialTheme.shapes.extraLarge,
                text = "Guided Breathing Exercises",
                onClick = {
                    onNavigate(ScreensRoute.Login.route)
                })
            SpacerHeight(2.hpr)
            TextButton(
                modifier = Modifier.padding(horizontal = 50.sdp),
                shape = MaterialTheme.shapes.extraLarge, text = "Insights", onClick = {
                    onNavigate(ScreensRoute.Login.route)
                })


            SpacerHeight(5.hpr)
            TitleLargeText(text = "Turn On Notifications?")
            SpacerHeight(5.hpr)
            /* TitleLargeText(text = "Sign Out" , fontWeight = FontWeight.Bold)*/
            LogoutButton(
                modifier = Modifier.padding(horizontal = 50.sdp),
                onClick = {
                    onNavigate(ScreensRoute.Login.route)
                })

            SpacerWeight(1f)
        }
    }
}