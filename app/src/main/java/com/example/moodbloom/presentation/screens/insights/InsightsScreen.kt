package com.example.moodbloom.presentation.screens.insights

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodbloom.MainViewModel
import com.example.moodbloom.domain.models.LogMoodsResponseModel
import com.example.moodbloom.extension.ResponseStates
import com.example.moodbloom.extension.SpacerHeight
import com.example.moodbloom.presentation.components.CardContainer
import com.example.moodbloom.presentation.components.HandleApiStates
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TopAppBar
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.ui.typo.BodyMediumText
import com.example.moodbloom.ui.typo.TitleMediumText

@Composable
fun InsightsScreenRoute(
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    mainViewModel: MainViewModel,
    viewModel: InsightsViewModel = hiltViewModel()
) {
    val listLogMoodState by viewModel.listLogMoodState.collectAsStateWithLifecycle()
    val generateMoodsInsightsState by viewModel.generateMoodsInsightsState.collectAsStateWithLifecycle()
    InsightsScreen(
        listLogMoodState=listLogMoodState,
        generateMoodsInsightsState=generateMoodsInsightsState,
        onNavigate = onNavigate,
        onBackClick = onBackClick,
    )
    LaunchedEffect(Unit) {
        viewModel.getUserAllMoodLogList(firebaseUser = mainViewModel.firebaseUser)
    }
}

@Composable
internal fun InsightsScreen(
    listLogMoodState: ResponseStates<List<LogMoodsResponseModel>> = ResponseStates.Idle,
    generateMoodsInsightsState: ResponseStates<String> = ResponseStates.Idle,
    promptsViewModel: PromptsViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {},


    onBackClick: () -> Unit
) {
    var insights by remember { mutableStateOf("") }
    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()

    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TopAppBar(title = "Insights") {
                onBackClick()
            }
            SpacerHeight(5.hpr)
            TitleMediumText(text = "Insights / How to Improve?")
            SpacerHeight(1.hpr)
            CardContainer{
                Column(
                    modifier = Modifier
                         .fillMaxWidth()
                        .heightIn(min = 300.sdp, max = 90.hpr)
                        .padding(10.sdp)
                        .verticalScroll(rememberScrollState())
                ) {
                    SpacerHeight(10.sdp)
                    BodyMediumText(text =insights, modifier = Modifier.fillMaxWidth().heightIn(min = 150.sdp))
                }
            }

        }
    }
    HandleApiStates(
        state = listLogMoodState, updatePrompt = promptsViewModel::updatePrompt
    ) { it ->
        LaunchedEffect(Unit) {

        }
    }
    HandleApiStates(
        state = generateMoodsInsightsState, updatePrompt = promptsViewModel::updatePrompt
    ) { it ->
        LaunchedEffect(Unit) {
            insights=it
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewInsightsScreen() {
    InsightsScreen {}
}