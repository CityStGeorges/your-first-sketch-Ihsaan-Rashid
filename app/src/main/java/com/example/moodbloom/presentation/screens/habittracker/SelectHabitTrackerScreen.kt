package com.example.moodbloom.presentation.screens.habittracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodbloom.presentation.MainViewModel
import com.example.moodbloom.data.DataHelper.getHabitsList
import com.example.moodbloom.domain.models.HabitTrackerModel
import com.example.moodbloom.utils.extension.SpacerHeight
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TopAppBar
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.screens.habittracker.items.ItemHabit
import com.example.moodbloom.presentation.routes.ScreensRoute


@Composable
fun SelectHabitTrackerRoute(
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    mainViewModel: MainViewModel,
    viewModel: HabitTrackerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    SelectHabitTrackerScreen(onHabitTrackerSelect={
        mainViewModel.selectedHabitTracker=it
        onNavigate(ScreensRoute.SetUpHabitTracker.route)
    } ,onBackClick = onBackClick)
}

@Composable
internal fun SelectHabitTrackerScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(),
    onHabitTrackerSelect: (HabitTrackerModel) -> Unit,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current
    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()

    val listHabitTracker: List<HabitTrackerModel> by remember { mutableStateOf(getHabitsList()) }
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(title = "Select Custom Habits") {
                onBackClick()
            }
            SpacerHeight(5.hpr)
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.spacedBy(2.sdp),
                verticalArrangement = Arrangement.spacedBy(3.sdp),
                columns = GridCells.Fixed(4)
            ) {
                itemsIndexed(listHabitTracker) { index, item ->
                    ItemHabit(
                        modifierContent = Modifier
                            .weight(1f)
                            .padding(10.sdp),
                        context = context,
                        item = item
                    ) {
                        onHabitTrackerSelect(listHabitTracker[index])
                    }
                }
            }
           /* SpacerWeight(1f)
            TextButton(modifier = Modifier.padding(horizontal = 10.sdp),
                text = "Next",
                onClick = {

                })
            SpacerHeight(5.hpr)*/
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SelectHabitTrackerPreview() {
    SelectHabitTrackerScreen(onHabitTrackerSelect = {}, onBackClick = {})
}
