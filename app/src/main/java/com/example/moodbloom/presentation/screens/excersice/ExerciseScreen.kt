package com.example.moodbloom.presentation.screens.excersice

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener
import android.speech.tts.UtteranceProgressListener
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.example.moodbloom.presentation.MainViewModel
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.ConfigurationModel
import com.example.moodbloom.domain.models.ExerciseModel
import com.example.moodbloom.utils.extension.SpacerHeight
import com.example.moodbloom.utils.extension.SpacerWidth
import com.example.moodbloom.utils.extension.formatTime
import com.example.moodbloom.presentation.components.CardContainer
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TextButton
import com.example.moodbloom.presentation.components.TopAppBar
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.ui.theme.md_theme_light_onSurface
import com.example.moodbloom.ui.typo.BodySmallText
import com.example.moodbloom.ui.typo.DisplayLargeText
import com.example.moodbloom.ui.typo.TitleLargeText
import com.example.moodbloom.ui.typo.TitleMediumText
import com.example.moodbloom.utils.TextToSpeechHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ExerciseRoute(
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    mainViewModel: MainViewModel,
    viewModel: ExerciseViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getExerciseList()
    }
    ExerciseScreen(
        selectedExerciseType = mainViewModel.selectedExercise,
        onNavigate = onNavigate,
        configurationModel = mainViewModel.configurationModel,
        onBackClick = onBackClick
    )
}

@Composable
internal fun ExerciseScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(), onNavigate: (String) -> Unit,
    selectedExerciseType: ExerciseModel? = null,
    configurationModel: ConfigurationModel = ConfigurationModel(),
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    var isCompleted by remember { mutableStateOf(false) }
    var isStarted by remember { mutableStateOf(false) }

    val mediaPlayer = MediaPlayer()
    DisposableEffect(Unit) {
        onDispose {
         mediaPlayer.stop()
        }}
    var isTextToVoiceRunning by remember { mutableStateOf(false) }

    var timeLeft by remember { mutableStateOf((selectedExerciseType?.timerMinutes ?: 0) * 60) }
    var isRunning by remember { mutableStateOf(false) }

    val textToSpeak by remember {
        mutableStateOf(
            "${selectedExerciseType?.title.orEmpty()}. Here is some guidance for this exercise. " +
                    selectedExerciseType?.guidelines?.joinToString(separator = " ") { it }.orEmpty()+" Let's Start"
        )
    }

    val coroutineScope = rememberCoroutineScope()
    fun startCountdown() {
        isStarted = true
        if (!isRunning) {
            isRunning = true
            isCompleted = false
            coroutineScope.launch {
                while (timeLeft > 0 && isRunning) {
                    delay(1000L)
                    timeLeft--
                }
                isRunning = false
                isCompleted = true
                if (configurationModel.isEnableRelaxingSound) {
                    mediaPlayer.stop()
                }
            }
        }
    }
    val textToSpeechHelper = remember { TextToSpeechHelper(context){
        isTextToVoiceRunning = false
        startCountdown()
    } }
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TopAppBar(title = "Guide Breathing Exercises") {
                onBackClick()
            }
            SpacerHeight(5.hpr)
            selectedExerciseType?.let { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ResourceImage(
                        image = LottieCompositionSpec.RawRes(item.icon),
                        modifier = Modifier.size(100.sdp)
                    )
                }
            }

            TitleLargeText(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = selectedExerciseType?.title ?: ""
            )
            SpacerHeight(1.hpr)
            CardContainer {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.sdp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (timeLeft == 0) {
                        DisplayLargeText(text = "00:00")
                    } else {
                        DisplayLargeText(text = timeLeft.formatTime())
                    }

                }
            }
            SpacerHeight(2.hpr)
            TitleMediumText(text = "Best For:")
            SpacerHeight(5.sdp)
            CardContainer {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.sdp)
                ) {
                    BodySmallText(
                        text = selectedExerciseType?.bestFor ?: "",
                        overrideColor = md_theme_light_onSurface
                    )
                }
            }
            SpacerHeight(2.hpr)
            TitleMediumText(text = "How to Do It:")
            SpacerHeight(5.sdp)
            CardContainer(modifier = Modifier.fillMaxWidth().weight(1f)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.sdp),
                    verticalArrangement = Arrangement.spacedBy(2.sdp)
                ) {
                    itemsIndexed(selectedExerciseType?.guidelines ?: listOf()) { index, item ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            //TitleSmallText(text = "${index+1}. ")
                            ResourceImage(
                                image = R.drawable.ic_bullet,
                                modifier = Modifier.size(10.sdp)
                            )
                            SpacerWidth(5.sdp)
                            BodySmallText(
                                text = item,
                                overrideColor = md_theme_light_onSurface
                            )
                        }

                    }
                }
            }
            SpacerHeight(2.hpr)
            TitleMediumText(text = "Tip:")
            SpacerHeight(5.sdp)
            CardContainer {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.sdp)
                ) {
                    BodySmallText(
                        text = selectedExerciseType?.tips ?: "",
                        overrideColor = md_theme_light_onSurface
                    )
                }
            }
            SpacerHeight(10.sdp)
            Row(modifier = Modifier.fillMaxWidth()) {
                if (!isStarted && !isCompleted && !isTextToVoiceRunning) {
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 10.sdp),
                        text = "Start",
                        onClick = {
                            if (configurationModel.isEnableVoiceGuidance) {
                                isTextToVoiceRunning = true
                                textToSpeechHelper.speak(textToSpeak)
                            } else {
                                startCountdown()
                            }
                            if (configurationModel.isEnableRelaxingSound) {
                                playSuccessTune(
                                    context = context,
                                    mediaPlayer = mediaPlayer
                                )
                            }
                        })
                } else if (isCompleted && !isRunning && !isTextToVoiceRunning) {
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 10.sdp),
                        text = "Restart",
                        onClick = {
                            timeLeft = selectedExerciseType?.timerMinutes ?: 0
                            if (configurationModel.isEnableRelaxingSound) {
                                playSuccessTune(context = context, mediaPlayer = mediaPlayer)
                            }
                            startCountdown()
                        })
                } else if (isRunning && !isTextToVoiceRunning) {
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 10.sdp),
                        primary = false,
                        text = "Stop",
                        onClick = {
                            if (configurationModel.isEnableRelaxingSound) {
                                mediaPlayer.stop()
                            }
                            onBackClick()
                        })
                }
            }
            SpacerHeight(5.hpr)
        }
    }
}


fun playSuccessTune(context: Context, mediaPlayer: MediaPlayer) {
    try {
        val notificationSoundUri = Uri.parse(
            "android.resource://" + context.packageName + "/" + R.raw.relax_music
        )

        mediaPlayer.reset()
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
        )
        mediaPlayer.setDataSource(context, notificationSoundUri)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener { mp ->
            mp.start()
        }
        mediaPlayer.setOnCompletionListener { mp ->
            mp.reset()
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseScreenPreview() {
    ExerciseScreen(onNavigate = {}, onBackClick = {})
}
