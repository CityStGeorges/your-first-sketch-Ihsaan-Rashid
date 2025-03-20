package com.example.moodbloom.presentation.screens

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
import com.example.moodbloom.extension.SpacerHeight
import com.example.moodbloom.presentation.components.CardContainer
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TopAppBar
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.screens.moodtrends.MoodsTrendsViewModel
import com.example.moodbloom.ui.typo.BodySmallText
import com.example.moodbloom.ui.typo.TitleLargeText
import com.example.moodbloom.ui.typo.TitleMediumText

@Composable
fun InsightsScreenRoute(
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    mainViewModel: MainViewModel,
    viewModel: MoodsTrendsViewModel = hiltViewModel()
) {

    InsightsScreen(
        onNavigate = onNavigate,
        onBackClick = onBackClick,
    )
    LaunchedEffect(Unit) {
       // viewModel.getLastDates("Daily")
    }
}

@Composable
internal fun InsightsScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {},


    onBackClick: () -> Unit
) {
    var moodSummary by remember { mutableStateOf("This is the dummy mood summary Text djfk  ksjdfj koj dsfk kjdsf aksejf mnf urmds the  sdjfh sdfjhuydsa fjsdf lkjds fsdnsdmf djfh") }
    var habitSummary by remember { mutableStateOf("This is the dummy Habit summary Text djfk  ksjdfj koj dsfk kjdsf aksejf mnf urmds the  sdjfh sdfjhuydsa fjsdf lkjds fsdnsdmf djfh") }
    var insights by remember { mutableStateOf("This is the dummy insights of mood and habit Text djfk  ksjdfj koj dsfk kjdsf aksejf mnf urmds the  sdjfh sdfjhuydsa fjsdf lkjds fsdnsdmf djfh") }
    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()

    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        ) {
            TopAppBar(title = "Insights") {
                onBackClick()
            }
            SpacerHeight(5.hpr)
            TitleLargeText(text = "Mood Summary")
            SpacerHeight(1.hpr)
            CardContainer{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.sdp)
                ) {
                    SpacerHeight(10.sdp)
                    BodySmallText(text =moodSummary, modifier = Modifier.fillMaxWidth().heightIn(min = 150.sdp),)
                }
            }
            SpacerHeight(2.hpr)
            TitleLargeText(text = "Habit Summary")
            SpacerHeight(1.hpr)
            CardContainer{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.sdp)
                ) {
                    SpacerHeight(10.sdp)
                    BodySmallText(text =habitSummary, modifier = Modifier.fillMaxWidth().heightIn(min = 150.sdp),)
                }
            }
            SpacerHeight(2.hpr)
            TitleMediumText(text = "Insights / How to Improve?")
            SpacerHeight(1.hpr)
            CardContainer{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.sdp)
                ) {
                    SpacerHeight(10.sdp)
                    BodySmallText(text =insights, modifier = Modifier.fillMaxWidth().heightIn(min = 150.sdp))
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewInsightsScreen() {
    InsightsScreen {}
}