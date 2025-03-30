package com.example.moodbloom.presentation.screens.logdailymood

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.example.moodbloom.presentation.MainViewModel
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.LogMoodsRequest
import com.example.moodbloom.utils.extension.MoodType
import com.example.moodbloom.utils.extension.ResponseStates
import com.example.moodbloom.utils.extension.SpacerHeight
import com.example.moodbloom.utils.extension.SpacerWeight
import com.example.moodbloom.presentation.components.HandleApiStates
import com.example.moodbloom.presentation.components.PromptTypeShow
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TextButton
import com.example.moodbloom.presentation.components.TextInputField
import com.example.moodbloom.presentation.components.TopAppBar
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.routes.ScreensRoute
import com.example.moodbloom.ui.typo.HeadlineMediumText

@Composable
fun LogDailyMoodRoute(
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    mainViewModel: MainViewModel,
    viewModel: LogDailyViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val logMoodState by viewModel.logMoodState.collectAsStateWithLifecycle()
    LogDailyMoodScreen(
        onNavigate = {
            viewModel.clearLogMoodState()
            onNavigate(it)
        },
        logMoodState = logMoodState,
        onBackClick = onBackClick,
        userId = mainViewModel.userModel?.uid ?: "",
        onLogMoodCall = viewModel::logMood
    )
}

@Composable
internal fun LogDailyMoodScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    logMoodState: ResponseStates<String> = ResponseStates.Idle,
    userId: String = "",
    onLogMoodCall: (LogMoodsRequest) -> Unit
) {

    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    var selectedMoodItem: MoodType? by remember {
        mutableStateOf(
            MoodType.VHAPPY
        )
    }
    var aboutMood by remember { mutableStateOf("") }
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(title = "Log your Mood today") {
                onBackClick()
            }
            SpacerHeight(5.hpr)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(3.sdp)) {
                itemsIndexed(getMoodsList()) { index, item ->
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.TopEnd) {
                        ItemMoodEmoji(item = item, selectedMoodItem = selectedMoodItem) {
                            selectedMoodItem = it
                        }
                    }
                }
            }
            SpacerHeight(2.hpr)
            selectedMoodItem?.let { item ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ResourceImage(
                        image = LottieCompositionSpec.RawRes(item.emoji),
                        modifier = Modifier.size(100.sdp)
                    )
                    HeadlineMediumText(text = item.title)
                }
            } ?: run {
                SpacerHeight(120.sdp)
            }
            TextInputField(
                value = aboutMood,
                modifier = Modifier.height(150.sdp),
                onValueChange = {
                    if (it.length < 200) {
                        aboutMood = it
                    }
                },
                placeholder = "Write about your mood today",
                label = "About your mood today (Optional)",
                minLines = 5,
                maxLines = 5
            )
            SpacerWeight(1f)
            TextButton(modifier = Modifier.padding(horizontal = 10.sdp),
                enabled = selectedMoodItem != null,
                text = "Save Mood Entry",
                onClick = {
                    onLogMoodCall(
                        LogMoodsRequest(
                            userId = userId,
                            title = selectedMoodItem?.title ?: "",
                            aboutMood = aboutMood,
                            type = selectedMoodItem?.type ?: "",
                            moodScore = selectedMoodItem?.moodScore ?: 0,
                        )
                    )
                })
            SpacerHeight(5.hpr)
        }
    }


    HandleApiStates(
        state = logMoodState, updatePrompt = promptsViewModel::updatePrompt
    ) { it ->
        LaunchedEffect(Unit) {
            var goToTrends = false
            promptsViewModel.updatePrompt(
                PromptTypeShow.Confirmation(img = R.drawable.ic_success,
                    title = "Mood entry saved!",
                    message = it,
                    positiveButtonText = "View mood trends",
                    positiveButtonClick = {
                        goToTrends = true

                    },
                    negativeButtonText = "Go to home",
                    negativeButtonClick = {
                    },
                    onDismiss = {
                        promptsViewModel.updatePrompt(null)
                        if (goToTrends) {
                            onNavigate(ScreensRoute.MoodTrends.route)
                        } else {
                            onBackClick()
                        }
                    })
            )
        }
    }
}

fun getMoodsList(): List<MoodType> {
    return listOf(
        MoodType.VHAPPY,
        MoodType.HAPPY,
        MoodType.NORMAL,
        MoodType.UNCERTAIN,
        MoodType.SAD,
        MoodType.VSAD,
        MoodType.ANGRY,
    )
}