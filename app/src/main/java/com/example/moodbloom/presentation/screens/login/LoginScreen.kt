package com.example.moodbloom.presentation.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ScreenContainer

@Composable
fun LoginScreenRoute(
    onNavigate: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    LoginScreen(onNavigate=onNavigate)



}

@Composable
internal fun LoginScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(), onNavigate: (String) -> Unit
) {

    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        }
    }
}