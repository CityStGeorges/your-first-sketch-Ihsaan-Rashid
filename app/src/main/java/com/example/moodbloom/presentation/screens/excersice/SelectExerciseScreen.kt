package com.example.moodbloom.presentation.screens.excersice

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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
import com.example.moodbloom.MainViewModel
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.ConfigurationModel
import com.example.moodbloom.domain.models.ExerciseModel
import com.example.moodbloom.extension.ResponseStates
import com.example.moodbloom.extension.SpacerHeight
import com.example.moodbloom.extension.SpacerWeight
import com.example.moodbloom.extension.SpacerWidth
import com.example.moodbloom.extension.showToast
import com.example.moodbloom.presentation.components.CardContainer
import com.example.moodbloom.presentation.components.DropdownSelectionField
import com.example.moodbloom.presentation.components.HandleApiStates
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TextButton
import com.example.moodbloom.presentation.components.TopAppBar
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.screens.home.ItemSwitcher
import com.example.moodbloom.presentation.screens.home.viewModel.ConfigurationViewModel
import com.example.moodbloom.routes.ScreensRoute
import com.example.moodbloom.ui.typo.BodyLargeText
import com.example.moodbloom.ui.typo.TitleSmallText

@Composable
fun SelectExerciseRoute(
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    mainViewModel: MainViewModel,
    configurationViewModel: ConfigurationViewModel = hiltViewModel(),
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    val exerciseList by viewModel.listExercise.collectAsStateWithLifecycle()
    val adOrUpdateConfigState by configurationViewModel.adOrUpdateConfigState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getExerciseList()
    }
    SelectExerciseScreen(
        listExercise=exerciseList,
        adOrUpdateConfigState=adOrUpdateConfigState,
        configurationModel=mainViewModel.configurationModel,
        onExerciseSelect = {
            mainViewModel.selectedExercise = it
            onNavigate(ScreensRoute.Exercise.route)
        },
        onBackClick = onBackClick
    )
}

@Composable
internal fun SelectExerciseScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(),
    listExercise:List<ExerciseModel> = listOf(),
    onExerciseSelect: (ExerciseModel) -> Unit,
    configurationModel: ConfigurationModel = ConfigurationModel(),
    updateConfiguration: (Boolean,Boolean) -> Unit = {_,_->},
    adOrUpdateConfigState: ResponseStates<String> = ResponseStates.Idle,
    updateConfigInMain: (ConfigurationModel) -> Unit = {},
    onBackClick: () -> Unit
) {

    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var selectedExerciseType: ExerciseModel? by remember { mutableStateOf(listExercise.takeIf {!it.isNullOrEmpty() }?.first()) }
    var isRelaxingSoundEnable by remember { mutableStateOf(configurationModel.isEnableRelaxingSound) }
    var isVoiceGuidanceEnable by remember { mutableStateOf(configurationModel.isEnableVoiceGuidance) }
    var totalMinutes by remember { mutableStateOf(selectedExerciseType?.timerMinutes?:3) }
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(title = "Select Your Breathing Exercises") {
                onBackClick()
            }
            SpacerHeight(5.hpr)
            selectedExerciseType?.let { item ->
                ResourceImage(
                    image = LottieCompositionSpec.RawRes(item.icon),
                    modifier = Modifier.size(100.sdp)
                )
            }
            SpacerHeight(2.hpr)
            DropdownSelectionField(
                label = "Exercise Type",
                placeholder = "Select Exercise Type",
                list = listExercise,
                selectedOption = selectedExerciseType?.title ?: "",
                dropDownItem = { pos, item ->
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        ResourceImage(
                            image = LottieCompositionSpec.RawRes(item.icon),
                            modifier = Modifier.size(30.sdp)
                        )
                        SpacerWidth(3.sdp)
                        BodyLargeText(text = item.title)
                    }
                },
                onValueChange = { pos, item ->
                    selectedExerciseType = item
                }
            )
            SpacerHeight(2.hpr)
            CardContainer(
                containerColor = MaterialTheme.colorScheme.background,
                borderColor = MaterialTheme.colorScheme.outline
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TitleSmallText(text = "Timer $totalMinutes Minutes")
                    SpacerWeight(1f)
                    ResourceImage(
                        image = R.drawable.ic_minus,
                        modifier = Modifier
                            .size(22.sdp)
                            .clickable {
                                if (totalMinutes > 1) {
                                    totalMinutes--
                                }
                            })
                    SpacerWidth(2.sdp)
                    ResourceImage(
                        image = R.drawable.ic_plus,
                        modifier = Modifier
                            .size(22.sdp)
                            .clickable {
                                if (totalMinutes < 10) {
                                    totalMinutes++
                                }
                            })
                }
            }

            SpacerHeight(2.hpr)

            ItemSwitcher(
                item = "Relaxing Sound",
                isChecked = isRelaxingSoundEnable,
                onCheckedChange = {
                    if (it != isRelaxingSoundEnable) {
                        isRelaxingSoundEnable = it
                        updateConfiguration(isRelaxingSoundEnable,isVoiceGuidanceEnable)
                    }
                })

            SpacerHeight(2.hpr)

            ItemSwitcher(
                item = "Voice Guidance",
                isChecked = isVoiceGuidanceEnable,
                onCheckedChange = {
                    if (it != isVoiceGuidanceEnable) {
                        isVoiceGuidanceEnable = it
                        updateConfiguration(isRelaxingSoundEnable,isVoiceGuidanceEnable)
                    }
                })

            SpacerWeight(1f)
            TextButton(
                enabled = selectedExerciseType!=null,
                modifier = Modifier.padding(horizontal = 10.sdp),
                text = "Continue",
                onClick = {
                    selectedExerciseType?.let {
                        onExerciseSelect(it.copy(relaxingSound = isRelaxingSoundEnable, timerMinutes = totalMinutes, voiceGuidance = isVoiceGuidanceEnable))
                    }
                })
            SpacerHeight(5.hpr)
        }
    }

    HandleApiStates(
        state = adOrUpdateConfigState,
        updatePrompt = promptsViewModel::updatePrompt,
        onFailure = {
            LaunchedEffect(Unit) {
                isRelaxingSoundEnable = !isRelaxingSoundEnable
                isVoiceGuidanceEnable = !isVoiceGuidanceEnable
                context.showToast(it)
            }
        },
        onSuccess = {
            LaunchedEffect(Unit) {
                updateConfigInMain(configurationModel.copy(isEnableRelaxingSound = isRelaxingSoundEnable, isEnableVoiceGuidance = isVoiceGuidanceEnable))
            }
        }
    )
}